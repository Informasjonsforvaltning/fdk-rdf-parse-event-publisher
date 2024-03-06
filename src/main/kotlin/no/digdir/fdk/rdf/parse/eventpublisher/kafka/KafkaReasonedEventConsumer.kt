package no.digdir.fdk.rdf.parse.eventpublisher.kafka

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
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component


@Component
class KafkaReasonedEventConsumer(
        private val producer: KafkaRdfParseEventProducer,
        private val rdfParserService: RdfParserService,
) {
    @KafkaListener(
            topics = [
                "dataset-events",
                "data-service-events",
                "concept-events",
                "information-model-events",
                "event-events",
                "service-events"],
            groupId = "fdk-rdf-parse-event-publisher",
            concurrency = "4",
            containerFactory = "kafkaListenerContainerFactory"
    )
    fun listen(record: ConsumerRecord<String, SpecificRecord>, ack: Acknowledgment) {
        LOGGER.debug("Received message - offset: " + record.offset())
        val event = record.value()
        try {
            event.let {
                if (it is DatasetEvent && it.type == DatasetEventType.DATASET_REASONED) {
                    parseAndProduce(it.fdkId.toString(), it.graph.toString(), it.timestamp, RdfParseResourceType.DATASET)
                } else if (it is DataServiceEvent && it.type == DataServiceEventType.DATA_SERVICE_REASONED) {
                    parseAndProduce(it.fdkId.toString(), it.graph.toString(), it.timestamp, RdfParseResourceType.DATA_SERVICE)
                } else if (it is ConceptEvent && it.type == ConceptEventType.CONCEPT_REASONED) {
                    parseAndProduce(it.fdkId.toString(), it.graph.toString(), it.timestamp, RdfParseResourceType.CONCEPT)
                } else if (it is InformationModelEvent && it.type == InformationModelEventType.INFORMATION_MODEL_REASONED) {
                    parseAndProduce(it.fdkId.toString(), it.graph.toString(), it.timestamp, RdfParseResourceType.INFORMATION_MODEL)
                } else if (it is ServiceEvent && it.type == ServiceEventType.SERVICE_REASONED) {
                    parseAndProduce(it.fdkId.toString(), it.graph.toString(), it.timestamp, RdfParseResourceType.SERVICE)
                } else if (it is EventEvent && it.type == EventEventType.EVENT_REASONED) {
                    parseAndProduce(it.fdkId.toString(), it.graph.toString(), it.timestamp, RdfParseResourceType.EVENT)
                }
            }
            ack.acknowledge()
        } catch (e: RecoverableParseException) {
            LOGGER.debug("Recoverable parsing error: " + e.message)
            ack.acknowledge()
        } catch (e: UnrecoverableParseException) {
            LOGGER.error("Unrecoverable parsing error: " + e.message)
        }
    }

    private fun parseAndProduce(fdkId: String, graph: String, timestamp: Long, type: RdfParseResourceType) {
        LOGGER.debug("Parse dataset - id: $fdkId")
        val json = rdfParserService.parseRdf(graph, type)
        producer.sendMessage(RdfParseEvent(type, fdkId, json.toString(), timestamp))
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(KafkaReasonedEventConsumer::class.java)
    }
}
