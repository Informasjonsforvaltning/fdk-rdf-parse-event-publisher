package no.digdir.fdk.rdf.parse.eventpublisher.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import no.digdir.fdk.rdf.parse.eventpublisher.configuration.ApplicationConfig
import no.digdir.fdk.rdf.parse.eventpublisher.exception.RecoverableParseException
import no.digdir.fdk.rdf.parse.eventpublisher.exception.UnrecoverableParseException
import no.fdk.rdf.parse.RdfParseResourceType
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate


@Service
class RdfParserService(
        private val applicationConfig: ApplicationConfig,
        private val restTemplate: RestTemplate = RestTemplate(),
        private val mapper: ObjectMapper = ObjectMapper()
) {
    private fun createRequest(rdf: String): HttpEntity<String> {
        val headers = HttpHeaders()
        headers.set("Content-Type", "text/turtle; charset=utf-8")
        headers.set("Accept", "application/json")
        headers.set("X-API-KEY", applicationConfig.rdfParserServiceApiKey)

        return HttpEntity(rdf.toByteArray(Charsets.UTF_8).decodeToString(), headers)
    }

    private fun getResourcePath(resourceType: RdfParseResourceType): String {
        return when (resourceType) {
            RdfParseResourceType.DATASET -> "dataset"
            RdfParseResourceType.DATA_SERVICE -> "data-service"
            RdfParseResourceType.CONCEPT -> "concept"
            RdfParseResourceType.INFORMATION_MODEL -> "information-model"
            RdfParseResourceType.SERVICE -> "service"
            RdfParseResourceType.EVENT -> "event"
        }
    }

    private fun getErrorFromResponse(e: HttpStatusCodeException): String {
        return try {
            val jsonNode = mapper.readTree(e.responseBodyAsString)
            jsonNode.get("detail").asText()
        } catch (e: Exception) {
            "Unknown error occurred when parsing RDF."
        }
    }

    fun parseRdf(rdf: String, resourceType: RdfParseResourceType): JsonNode {
        try {
            // Call RDF parser service
            val resourcePath = getResourcePath(resourceType)
            val jsonResponse = restTemplate.postForObject("${applicationConfig.rdfParserServiceUrl}/$resourcePath", createRequest(rdf), String::class.java)

            // Parse JSON response
            return mapper.readTree(jsonResponse)
        } catch (e: HttpClientErrorException) {
            throw RecoverableParseException("Error parsing RDF: ${getErrorFromResponse(e)}")
        } catch (e: Exception) {
            throw UnrecoverableParseException("Error parsing RDF: ${e.message}")
        }
    }
}
