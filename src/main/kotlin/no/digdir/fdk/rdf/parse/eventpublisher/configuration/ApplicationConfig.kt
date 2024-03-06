package no.digdir.fdk.rdf.parse.eventpublisher.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
open class ApplicationConfig {
    @Value("\${application.rdfparser.url}")
    lateinit var rdfParserServiceUrl: String

    @Value("\${application.rdfparser.api-key}")
    lateinit var rdfParserServiceApiKey: String
}
