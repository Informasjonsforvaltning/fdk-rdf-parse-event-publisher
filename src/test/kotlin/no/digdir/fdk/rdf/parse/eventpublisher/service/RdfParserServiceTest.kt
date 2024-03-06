package no.digdir.fdk.rdf.parse.eventpublisher.service

import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import no.digdir.fdk.rdf.parse.eventpublisher.configuration.ApplicationConfig
import no.digdir.fdk.rdf.parse.eventpublisher.exception.RecoverableParseException
import no.digdir.fdk.rdf.parse.eventpublisher.exception.UnrecoverableParseException
import no.digdir.fdk.rdf.parse.eventpublisher.utils.TestUtils.Companion.createRdfRequest
import no.fdk.rdf.parse.RdfParseResourceType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatusCode
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.RestTemplate
import kotlin.test.assertEquals

@ActiveProfiles("test")
class RdfParserServiceTest {
    private val applicationConfig: ApplicationConfig = mockk()
    private val restTemplate: RestTemplate = mockk()
    private val rdfParserService = RdfParserService(applicationConfig, restTemplate, ObjectMapper())

    @Test
    fun `parseRdf should return json`() {
        val rdf = "<http://example.com/subject> <http://example.com/predicate> <http://example.com/object> ."
        val resourceType = RdfParseResourceType.DATASET

        val baseUrl = "http://example.com"
        every { applicationConfig.rdfParserServiceUrl } returns baseUrl
        every { applicationConfig.rdfParserServiceApiKey } returns "test-key"
        every { restTemplate.postForObject("$baseUrl/dataset", createRdfRequest(rdf), String::class.java) } returns "{\"data\": \"my-parsed-rdf\"}"

        val result = rdfParserService.parseRdf(rdf, resourceType)
        assertEquals("my-parsed-rdf", result.get("data")?.asText())
    }

    @Test
    fun `parseRdf should throw recoverable exception`() {
        val rdf = "invalid rdf"
        val resourceType = RdfParseResourceType.DATASET

        val baseUrl = "http://example.com"
        every { applicationConfig.rdfParserServiceUrl } returns baseUrl
        every { applicationConfig.rdfParserServiceApiKey } returns "test-key"
        every {
            restTemplate.postForObject("$baseUrl/dataset", createRdfRequest(rdf), String::class.java)
        } throws HttpClientErrorException(HttpStatusCode.valueOf(400),
                "", "{\"detail\": \"invalid rdf\"}".toByteArray(), null)

        val throws = assertThrows<RecoverableParseException> {
            rdfParserService.parseRdf(rdf, resourceType)
        }
        assertEquals("Error parsing RDF: invalid rdf", throws.message)
    }

    @Test
    fun `parseRdf should throw unrecoverable exception`() {
        val resourceType = RdfParseResourceType.DATASET

        val baseUrl = "http://example.com"
        every { applicationConfig.rdfParserServiceUrl } returns baseUrl
        every { applicationConfig.rdfParserServiceApiKey } returns "test-key"
        every {
            restTemplate.postForObject("$baseUrl/dataset", any(), String::class.java)
        } throws HttpServerErrorException(HttpStatusCode.valueOf(500))

        val throws = assertThrows<UnrecoverableParseException> {
            rdfParserService.parseRdf("", resourceType)
        }
        assertEquals("Error parsing RDF: 500 INTERNAL_SERVER_ERROR", throws.message)
    }
}
