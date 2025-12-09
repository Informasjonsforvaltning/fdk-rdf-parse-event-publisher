package no.digdir.fdk.rdf.parse.eventpublisher.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import no.digdir.fdk.rdf.parse.eventpublisher.exception.RecoverableParseException
import no.digdir.fdk.rdf.parse.eventpublisher.exception.UnrecoverableParseException
import no.digdir.fdk.rdf.parse.eventpublisher.service.RdfParserService
import no.fdk.concept.ConceptEvent
import no.fdk.concept.ConceptEventType
import no.fdk.rdf.parse.RdfParseEvent
import no.fdk.rdf.parse.RdfParseResourceType
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.junit.jupiter.api.Test
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.Acknowledgment
import org.springframework.test.context.ActiveProfiles
import java.time.Duration
import java.util.concurrent.CompletableFuture
import kotlin.test.assertEquals

@ActiveProfiles("test")
class KafkaReasonedEventConsumerTest {
    private val rdfParserService: RdfParserService = mockk(relaxed = true)
    private val kafkaTemplate: KafkaTemplate<String, RdfParseEvent> = mockk(relaxed = true)
    private val harvestEventKafkaTemplate: KafkaTemplate<String, no.fdk.harvest.HarvestEvent> = mockk(relaxed = true)
    private val ack: Acknowledgment = mockk(relaxed = true)
    private val kafkaRdfParseEventProducer = KafkaRdfParseEventProducer(kafkaTemplate)
    private val harvestEventProducer = KafkaHarvestEventProducer(harvestEventKafkaTemplate)
    private val circuitBreaker = KafkaReasonedEventCircuitBreaker(kafkaRdfParseEventProducer, harvestEventProducer, rdfParserService)
    private val kafkaReasonedEventConsumer = KafkaReasonedEventConsumer(circuitBreaker)
    private val mapper = ObjectMapper()

    @Test
    fun `listen should produce a rdf parse event`() {
        val parsedJson = "{\"data\":\"my-parsed-rdf\"}"
        every { rdfParserService.parseRdf(any(), any()) } returns mapper.readTree(parsedJson)
        every { kafkaTemplate.send(any(), any()) } returns CompletableFuture()
        every { harvestEventKafkaTemplate.send(any(), any()) } returns CompletableFuture()
        every { ack.acknowledge() } returns Unit
        every { ack.nack(Duration.ZERO) } returns Unit

        val conceptEvent = ConceptEvent(ConceptEventType.CONCEPT_REASONED, "harvest-run-id", "uri", "my-id", "graph", System.currentTimeMillis())
        kafkaReasonedEventConsumer.listen(
            record = ConsumerRecord("concept-events", 0, 0, "my-id", conceptEvent),
            ack = ack
        )

        verify {
            kafkaTemplate.send(withArg {
                assertEquals("rdf-parse-events", it)
            }, withArg {
                assertEquals(conceptEvent.fdkId.toString(), it.fdkId.toString())
                assertEquals(RdfParseResourceType.CONCEPT, it.resourceType)
                assertEquals(parsedJson, it.data)
                assertEquals(conceptEvent.timestamp, it.timestamp)
            })
            harvestEventKafkaTemplate.send(any(), any())
            ack.acknowledge()
        }
    }

    @Test
    fun `listen should acknowledge when a recoverable exception occurs`() {
        every {
            rdfParserService.parseRdf(
                any(),
                any()
            )
        } throws RecoverableParseException("Error parsing RDF: invalid rdf")
        every { harvestEventKafkaTemplate.send(any(), any()) } returns CompletableFuture()
        every { ack.acknowledge() } returns Unit
        every { ack.nack(Duration.ZERO) } returns Unit

        val conceptEvent = ConceptEvent(ConceptEventType.CONCEPT_REASONED, "harvest-run-id", "uri", "my-id", "graph", System.currentTimeMillis())
        kafkaReasonedEventConsumer.listen(
            record = ConsumerRecord("concept-events", 0, 0, "my-id", conceptEvent),
            ack = ack
        )

        verify(exactly = 0) { kafkaTemplate.send(any(), any()) }
        verify(exactly = 1) { harvestEventKafkaTemplate.send(any(), any()) }
        verify(exactly = 1) { ack.acknowledge() }
        verify(exactly = 0) { ack.nack(Duration.ZERO) }
    }

    @Test
    fun `listen should not acknowledge when a unrecoverable exception occurs`() {
        every { rdfParserService.parseRdf(any(), any()) } throws UnrecoverableParseException("Error parsing RDF")
        every { harvestEventKafkaTemplate.send(any(), any()) } returns CompletableFuture()
        every { ack.nack(Duration.ZERO) } returns Unit

        val conceptEvent = ConceptEvent(ConceptEventType.CONCEPT_REASONED, "harvest-run-id", "uri", "my-id", "graph", System.currentTimeMillis())
        kafkaReasonedEventConsumer.listen(
            record = ConsumerRecord("concept-events", 0, 0, "my-id", conceptEvent),
            ack = ack
        )

        verify(exactly = 0) { kafkaTemplate.send(any(), any()) }
        verify(exactly = 1) { harvestEventKafkaTemplate.send(any(), any()) }
        verify(exactly = 0) { ack.acknowledge() }
        verify(exactly = 1) { ack.nack(Duration.ZERO) }
    }
}
