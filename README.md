# FDK RDF Parse Event Publisher

This application is responsible for publishing RDF parse events. The service consumes reasoned events (Kafka), parses
RDF to JSON using [fdk-rdf-parser-service](https://github.com/Informasjonsforvaltning/fdk-rdf-parser-service)

For a broader understanding of the system’s context, refer to
the [architecture documentation](https://github.com/Informasjonsforvaltning/architecture-documentation) wiki. For more
specific context on this application, see the **Harvesting** subsystem section.

### Prerequisites

Ensure you have the following installed:

- Java 21
- Maven
- Docker

### Running locally

#### Clone the repository

```sh
git clone https://github.com/Informasjonsforvaltning/fdk-rdf-parse-event-publisher.git
cd fdk-rdf-parse-event-publisher
```

#### Generate sources

Kafka messages are serialized using Avro. Avro schemas are located in ```kafka/schemas```. To generate sources from Avro
schema, run the following command:

```sh
mvn generate-sources    
```

#### Start Kafka cluster and setup topics/schemas

Topics and schemas are set up automatically when starting the Kafka cluster. Docker compose uses the scripts
```create-topics.sh``` and ```create-schemas.sh``` to set up topics and schemas.

```sh
docker-compose up -d
```

If you have problems starting kafka, check if all health checks are ok. Make sure number at the end (after 'grep')
matches desired topics.

#### Start application

```sh
mvn spring-boot:run -Dspring-boot.run.profiles=develop
```

#### Produce messages

Check if schema id is correct in the script. This should be 1 if there is only one schema in your registry.

```sh
sh ./kafka/produce-messages.sh
```

### Running tests

```sh
mvn verify
```
