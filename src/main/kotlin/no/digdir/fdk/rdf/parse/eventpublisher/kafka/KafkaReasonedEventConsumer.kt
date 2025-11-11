package no.digdir.fdk.rdf.parse.eventpublisher.kafka

import no.digdir.fdk.rdf.parse.eventpublisher.exception.RecoverableParseException
import no.digdir.fdk.rdf.parse.eventpublisher.exception.UnrecoverableParseException
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component
import java.time.Duration


@Component
class KafkaReasonedEventConsumer(
    private val circuitBreaker: KafkaReasonedEventCircuitBreaker
) {
    @KafkaListener(
        topics = [
            "concept-events",
            "event-events",
            "service-events"],
        groupId = "fdk-rdf-parse-event-publisher",
        concurrency = "4",
        containerFactory = "kafkaListenerContainerFactory",
        id = "rdf-parse"
    )
    fun listen(record: ConsumerRecord<String, SpecificRecord>, ack: Acknowledgment) {
        try {
            circuitBreaker.process(record)
            ack.acknowledge()
        } catch (e: RecoverableParseException) {
            ack.acknowledge()
        } catch (e: UnrecoverableParseException) {
            ack.nack(Duration.ZERO)
        }
    }
}
