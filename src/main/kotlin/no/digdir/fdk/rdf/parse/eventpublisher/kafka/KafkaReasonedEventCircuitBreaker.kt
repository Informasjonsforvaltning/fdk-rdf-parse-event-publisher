package no.digdir.fdk.rdf.parse.eventpublisher.kafka

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.micrometer.core.instrument.Metrics
import no.digdir.fdk.rdf.parse.eventpublisher.exception.RecoverableParseException
import no.digdir.fdk.rdf.parse.eventpublisher.exception.UnrecoverableParseException
import no.digdir.fdk.rdf.parse.eventpublisher.service.RdfParserService
import no.fdk.concept.ConceptEvent
import no.fdk.concept.ConceptEventType
import no.fdk.event.EventEvent
import no.fdk.event.EventEventType
import no.fdk.harvest.DataType
import no.fdk.harvest.HarvestEvent
import no.fdk.harvest.HarvestPhase
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
import java.time.Instant


@Component
open class KafkaReasonedEventCircuitBreaker(
    private val producer: KafkaRdfParseEventProducer,
    private val harvestEventProducer: KafkaHarvestEventProducer,
    private val rdfParserService: RdfParserService,
) {
    @CircuitBreaker(name = "rdf-parse")
    open fun process(record: ConsumerRecord<String, SpecificRecord>) {
        LOGGER.debug("Received message - offset: " + record.offset())
        val event = record.value()

        val resourceType = when (event) {
            is ConceptEvent -> RdfParseResourceType.CONCEPT
            is ServiceEvent -> RdfParseResourceType.SERVICE
            is EventEvent -> RdfParseResourceType.EVENT
            else -> throw UnrecoverableParseException("Unknown event type")
        }

        val harvestRunId = when (event) {
            is ConceptEvent -> event.harvestRunId?.toString()
            is ServiceEvent -> event.harvestRunId?.toString()
            is EventEvent -> event.harvestRunId?.toString()
            else -> null
        }

        val uri = when (event) {
            is ConceptEvent -> event.uri?.toString()
            is ServiceEvent -> event.uri?.toString()
            is EventEvent -> event.uri?.toString()
            else -> null
        }

        val fdkId = when (event) {
            is ConceptEvent -> event.fdkId?.toString()
            is ServiceEvent -> event.fdkId?.toString()
            is EventEvent -> event.fdkId?.toString()
            else -> null
        }

        val eventTimestamp = when (event) {
            is ConceptEvent -> event.timestamp
            is ServiceEvent -> event.timestamp
            is EventEvent -> event.timestamp
            else -> Instant.now().toEpochMilli()
        }

        val startTime = Instant.now().toEpochMilli()
        var endTime: Long? = null

        try {
            event.let {
                if (it is ConceptEvent && it.type == ConceptEventType.CONCEPT_REASONED) {
                    parseAndProduce(it.fdkId.toString(), it.graph.toString(), it.timestamp, resourceType, harvestRunId, uri)
                } else if (it is ServiceEvent && it.type == ServiceEventType.SERVICE_REASONED) {
                    parseAndProduce(it.fdkId.toString(), it.graph.toString(), it.timestamp, resourceType, harvestRunId, uri)
                } else if (it is EventEvent && it.type == EventEventType.EVENT_REASONED) {
                    parseAndProduce(it.fdkId.toString(), it.graph.toString(), it.timestamp, resourceType, harvestRunId, uri)
                }
            }
            endTime = Instant.now().toEpochMilli()
            // Produce harvest event for successful parsing
            produceHarvestEvent(resourceType, harvestRunId, uri, fdkId, eventTimestamp, startTime, endTime, null)
        } catch (e: RecoverableParseException) {
            endTime = Instant.now().toEpochMilli()
            LOGGER.debug("Recoverable parsing error: " + e.message)
            Metrics.counter(
                "rdf_parse_error",
                "type", resourceType.toString().lowercase()
            ).increment()
            // Produce harvest event for failed parsing
            produceHarvestEvent(resourceType, harvestRunId, uri, fdkId, eventTimestamp, startTime, endTime, e.message)
            throw e
        } catch (e: UnrecoverableParseException) {
            endTime = Instant.now().toEpochMilli()
            LOGGER.error("Unrecoverable parsing error: " + e.message)
            Metrics.counter(
                "rdf_parse_error",
                "type", resourceType.toString().lowercase()
            ).increment()
            // Produce harvest event for failed parsing
            produceHarvestEvent(resourceType, harvestRunId, uri, fdkId, eventTimestamp, startTime, endTime, e.message)
            throw e
        }
    }

    private fun parseAndProduce(fdkId: String, graph: String, timestamp: Long, type: RdfParseResourceType, harvestRunId: String?, uri: String?) {
        val timeElapsed = measureTimedValue {
            LOGGER.debug("Parse resource - id: $fdkId")
            val json = rdfParserService.parseRdf(graph, type)
            val rdfParseEvent = RdfParseEvent.newBuilder()
                .setResourceType(type)
                .setFdkId(fdkId)
                .setData(json.toString())
                .setTimestamp(timestamp)
                .setHarvestRunId(harvestRunId)
                .setUri(uri)
                .build()
            producer.sendMessage(rdfParseEvent)
        }
        Metrics.timer(
            "rdf_parse",
            "type", type.toString().lowercase()
        ).record(timeElapsed.duration.toJavaDuration())
    }

    private fun produceHarvestEvent(
        resourceType: RdfParseResourceType,
        harvestRunId: String?,
        uri: String?,
        fdkId: String?,
        timestamp: Long,
        startTime: Long,
        endTime: Long?,
        errorMessage: String?
    ) {
        if (harvestRunId == null) {
            LOGGER.debug("Skipping harvest event production - harvestRunId is null")
            return
        }

        val dataType = when (resourceType) {
            RdfParseResourceType.CONCEPT -> DataType.concept
            RdfParseResourceType.SERVICE -> DataType.dataservice
            RdfParseResourceType.EVENT -> DataType.event
            RdfParseResourceType.DATASET -> DataType.dataset
            RdfParseResourceType.DATA_SERVICE -> DataType.dataservice
            RdfParseResourceType.INFORMATION_MODEL -> DataType.informationmodel
        }

        val startTimeString = Instant.ofEpochMilli(startTime).toString()
        val endTimeString = endTime?.let { Instant.ofEpochMilli(it).toString() }

        val harvestEvent = HarvestEvent.newBuilder()
            .setPhase(HarvestPhase.RDF_PARSING)
            .setDataSourceId(null)
            .setRunId(harvestRunId)
            .setDataType(dataType)
            .setDataSourceUrl(null)
            .setAcceptHeader(null)
            .setFdkId(fdkId)
            .setResourceUri(uri)
            .setStartTime(startTimeString)
            .setEndTime(endTimeString)
            .setErrorMessage(errorMessage)
            .setChangedResourcesCount(null)
            .setUnchangedResourcesCount(null)
            .setRemovedResourcesCount(null)
            .build()

        harvestEventProducer.sendMessage(harvestEvent)
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(KafkaReasonedEventCircuitBreaker::class.java)
    }
}
