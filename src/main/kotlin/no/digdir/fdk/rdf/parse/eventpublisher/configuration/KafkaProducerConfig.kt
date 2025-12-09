package no.digdir.fdk.rdf.parse.eventpublisher.configuration

import io.confluent.kafka.serializers.KafkaAvroSerializer
import no.fdk.harvest.HarvestEvent
import no.fdk.rdf.parse.RdfParseEvent
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import java.util.*

open class KafkaProducerConfig {
    @Value("\${spring.kafka.bootstrap-servers}")
    private lateinit var bootstrapServers: String

    @Value("\${spring.kafka.properties.schema.registry.url}")
    private lateinit var schemaRegistryUrl: String

    @Bean
    open fun rdfParseEventProducerFactory(): ProducerFactory<String, RdfParseEvent> {
        val props: MutableMap<String, Any> = HashMap()
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = KafkaAvroSerializer::class.java
        props["schema.registry.url"] = schemaRegistryUrl
        props["specific.avro.reader"] = true
        props["auto.register.schemas"] = false
        props["use.latest.version"] = true
        props["value.subject.name.strategy"] = "io.confluent.kafka.serializers.subject.RecordNameStrategy"
        props["key.subject.name.strategy"] = "io.confluent.kafka.serializers.subject.RecordNameStrategy"
        props[ProducerConfig.COMPRESSION_TYPE_CONFIG] = "snappy"
        return DefaultKafkaProducerFactory(props)
    }

    @Bean
    open fun harvestEventProducerFactory(): ProducerFactory<String, HarvestEvent> {
        val props: MutableMap<String, Any> = HashMap()
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = KafkaAvroSerializer::class.java
        props["schema.registry.url"] = schemaRegistryUrl
        props["specific.avro.reader"] = true
        props["auto.register.schemas"] = false
        props["use.latest.version"] = true
        props["value.subject.name.strategy"] = "io.confluent.kafka.serializers.subject.RecordNameStrategy"
        props["key.subject.name.strategy"] = "io.confluent.kafka.serializers.subject.RecordNameStrategy"
        props[ProducerConfig.COMPRESSION_TYPE_CONFIG] = "snappy"
        return DefaultKafkaProducerFactory(props)
    }

    @Bean
    open fun kafkaTemplate(rdfParseEventProducerFactory: ProducerFactory<String, RdfParseEvent>): KafkaTemplate<String, RdfParseEvent> {
        return KafkaTemplate(rdfParseEventProducerFactory)
    }

    @Bean
    open fun harvestEventKafkaTemplate(harvestEventProducerFactory: ProducerFactory<String, HarvestEvent>): KafkaTemplate<String, HarvestEvent> {
        return KafkaTemplate(harvestEventProducerFactory)
    }
}
