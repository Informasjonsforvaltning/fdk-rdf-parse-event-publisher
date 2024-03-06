package no.digdir.fdk.rdf.parse.eventpublisher.utils

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders

class TestUtils {
    companion object {
        fun createRdfRequest(rdf: String): HttpEntity<String> {
            val headers = HttpHeaders()
            headers.set("Content-Type", "text/turtle; charset=utf-8")
            headers.set("Accept", "application/json")
            headers.set("X-API-KEY", "test-key")

            return HttpEntity(rdf, headers)
        }
    }
}
