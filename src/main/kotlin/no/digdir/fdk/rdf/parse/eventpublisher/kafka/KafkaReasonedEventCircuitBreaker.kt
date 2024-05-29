package no.digdir.fdk.rdf.parse.eventpublisher.kafka

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.micrometer.core.instrument.Metrics
import no.digdir.fdk.rdf.parse.eventpublisher.exception.RecoverableParseException
import no.digdir.fdk.rdf.parse.eventpublisher.exception.UnrecoverableParseException
import no.digdir.fdk.rdf.parse.eventpublisher.service.RdfParserService
import no.fdk.concept.ConceptEvent
import no.fdk.concept.ConceptEventType
import no.fdk.dataservice.DataServiceEvent
import no.fdk.dataservice.DataServiceEventType
import no.fdk.dataset.DatasetEvent
import no.fdk.dataset.DatasetEventType
import no.fdk.event.EventEvent
import no.fdk.event.EventEventType
import no.fdk.informationmodel.InformationModelEvent
import no.fdk.informationmodel.InformationModelEventType
import no.fdk.rdf.parse.RdfParseEvent
import no.fdk.rdf.parse.RdfParseResourceType
import no.fdk.service.ServiceEvent
import no.fdk.service.ServiceEventType
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import kotlin.time.measureTimedValue
import kotlin.time.toJavaDuration


@Component
open class KafkaReasonedEventCircuitBreaker(
    private val producer: KafkaRdfParseEventProducer,
    private val rdfParserService: RdfParserService,
) {
    @CircuitBreaker(name = "rdf-parse")
    open fun process(record: ConsumerRecord<String, SpecificRecord>) {
        LOGGER.debug("Received message - offset: " + record.offset())
        val event = record.value()

        val resourceType = when (event) {
            is DatasetEvent -> RdfParseResourceType.DATASET
            is DataServiceEvent -> RdfParseResourceType.DATA_SERVICE
            is ConceptEvent -> RdfParseResourceType.CONCEPT
            is InformationModelEvent -> RdfParseResourceType.INFORMATION_MODEL
            is ServiceEvent -> RdfParseResourceType.SERVICE
            is EventEvent -> RdfParseResourceType.EVENT
            else -> throw UnrecoverableParseException("Unknown event type")
        }

        try {
            event.let {
                if (it is DatasetEvent && it.type == DatasetEventType.DATASET_REASONED) {
                    parseAndProduce(it.fdkId.toString(), it.graph.toString(), it.timestamp, resourceType)
                } else if (it is DataServiceEvent && it.type == DataServiceEventType.DATA_SERVICE_REASONED) {
                    parseAndProduce(it.fdkId.toString(), it.graph.toString(), it.timestamp, resourceType)
                } else if (it is ConceptEvent && it.type == ConceptEventType.CONCEPT_REASONED) {
                    parseAndProduce(it.fdkId.toString(), it.graph.toString(), it.timestamp, resourceType)
                } else if (it is InformationModelEvent && it.type == InformationModelEventType.INFORMATION_MODEL_REASONED) {
                    parseAndProduce(it.fdkId.toString(), it.graph.toString(), it.timestamp, resourceType)
                } else if (it is ServiceEvent && it.type == ServiceEventType.SERVICE_REASONED) {
                    parseAndProduce(it.fdkId.toString(), it.graph.toString(), it.timestamp, resourceType)
                } else if (it is EventEvent && it.type == EventEventType.EVENT_REASONED) {
                    parseAndProduce(it.fdkId.toString(), it.graph.toString(), it.timestamp, resourceType)
                }
            }
        } catch (e: RecoverableParseException) {
            LOGGER.debug("Recoverable parsing error: ", e)
            Metrics.counter(
                "rdf_parse_error",
                "type", resourceType.toString().lowercase()
            ).increment()
            throw e
        } catch (e: UnrecoverableParseException) {
            LOGGER.error("Unrecoverable parsing error: ", e)
            Metrics.counter(
                "rdf_parse_error",
                "type", resourceType.toString().lowercase()
            ).increment()
            throw e
        }
    }

    private fun parseAndProduce(fdkId: String, graph: String, timestamp: Long, type: RdfParseResourceType) {
        val timeElapsed = measureTimedValue {
            LOGGER.debug("Parse dataset - id: $fdkId")
            val json = rdfParserService.parseRdf(graph, type)
            producer.sendMessage(RdfParseEvent(type, fdkId, json.toString(), timestamp))
        }
        Metrics.timer(
            "rdf_parse",
            "type", type.toString().lowercase()
        ).record(timeElapsed.duration.toJavaDuration())
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(KafkaReasonedEventCircuitBreaker::class.java)
    }
}
