#!/bin/bash

schema_id=$1
messages='{ "type": "DATASET_REASONED", "fdkId": "6467f35c-f246-48f5-956c-616b949e52e6", "timestamp": 1647698566000, "graph": "@prefix adms: <http://www.w3.org/ns/adms#> . @prefix cpsv: <http://purl.org/vocab/cpsv#> . @prefix cpsvno: <https://data.norge.no/vocabulary/cpsvno#> . @prefix dcat: <http://www.w3.org/ns/dcat#> . @prefix dct: <http://purl.org/dc/terms/> . @prefix dqv: <http://www.w3.org/ns/dqv#> . @prefix eli: <http://data.europa.eu/eli/ontology#> . @prefix foaf: <http://xmlns.com/foaf/0.1/> . @prefix iso: <http://iso.org/25012/2008/dataquality/> . @prefix oa: <http://www.w3.org/ns/oa#> . @prefix prov: <http://www.w3.org/ns/prov#> . @prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> . @prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> . @prefix schema: <http://schema.org/> . @prefix skos: <http://www.w3.org/2004/02/skos/core#> . @prefix vcard: <http://www.w3.org/2006/vcard/ns#> . @prefix xsd: <http://www.w3.org/2001/XMLSchema#> . <https://registrering.fellesdatakatalog.digdir.no/catalogs/971277882/datasets/29a2bf37-5867-4c90-bc74-5a8c4e118572> rdf:type dcat:Dataset ; dct:accessRights <http://publications.europa.eu/resource/authority/access-right/PUBLIC> ; dct:description \"Visning over all norsk offentlig bistand fra 1960 til siste kalenderår sortert etter partnerorganisasjoner.\"@nb ; dct:identifier \"https://registrering.fellesdatakatalog.digdir.no/catalogs/971277882/datasets/29a2bf37-5867-4c90-bc74-5a8c4e118572\" ; dct:language <http://publications.europa.eu/resource/authority/language/NOR> , <http://publications.europa.eu/resource/authority/language/ENG> ; dct:provenance <http://data.brreg.no/datakatalog/provinens/nasjonal> ; dct:publisher <https://organization-catalogue.fellesdatakatalog.digdir.no/organizations/971277882> ; dct:title \"Bistandsresultater - bistand etter partner\"@nb ; dct:type \"Data\" ; dcat:contactPoint [ rdf:type vcard:Organization ; vcard:hasEmail <mailto:resultater@norad.no> ] ; dcat:distribution [ rdf:type dcat:Distribution ; dct:description \"Norsk bistand i tall etter partner\"@nb ; dct:format <https://www.iana.org/assignments/media-types/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet> , <https://www.iana.org/assignments/media-types/text/csv> ; dct:license <http://data.norge.no/nlod/no/2.0> ; dct:title \"Bistandsresultater - bistand etter partner\"@nb ; dcat:accessURL <https://resultater.norad.no/partner/> ] ; dcat:keyword \"oda\"@nb , \"norad\"@nb , \"bistand\"@nb ; dcat:landingPage <https://resultater.norad.no/partner/> ; dcat:theme <http://publications.europa.eu/resource/authority/data-theme/INTR> ; dqv:hasQualityAnnotation [ rdf:type dqv:QualityAnnotation ; dqv:inDimension iso:Currentness ] ; prov:qualifiedAttribution [ rdf:type prov:Attribution ; dcat:hadRole <http://registry.it.csiro.au/def/isotc211/CI_RoleCode/contributor> ; prov:agent <https://data.brreg.no/enhetsregisteret/api/enheter/971277882> ] . <http://publications.europa.eu/resource/authority/language/ENG> rdf:type dct:LinguisticSystem ; <http://publications.europa.eu/ontology/authority/authority-code> \"ENG\" ; skos:prefLabel \"Engelsk\"@nb . <http://publications.europa.eu/resource/authority/language/NOR> rdf:type dct:LinguisticSystem ; <http://publications.europa.eu/ontology/authority/authority-code> \"NOR\" ; skos:prefLabel \"Norsk\"@nb ." }
          { "type": "DATASET_REASONED", "fdkId": "28df0d6b-8e75-4136-84f3-f3f3c65fbd7c", "timestamp": 1647698566000, "graph": "@prefix adms: <http://www.w3.org/ns/adms#> . @prefix cpsv: <http://purl.org/vocab/cpsv#> . @prefix cpsvno: <https://data.norge.no/vocabulary/cpsvno#> . @prefix dcat: <http://www.w3.org/ns/dcat#> . @prefix dct: <http://purl.org/dc/terms/> . @prefix dqv: <http://www.w3.org/ns/dqv#> . @prefix eli: <http://data.europa.eu/eli/ontology#> . @prefix foaf: <http://xmlns.com/foaf/0.1/> . @prefix iso: <http://iso.org/25012/2008/dataquality/> . @prefix oa: <http://www.w3.org/ns/oa#> . @prefix prov: <http://www.w3.org/ns/prov#> . @prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> . @prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> . @prefix schema: <http://schema.org/> . @prefix skos: <http://www.w3.org/2004/02/skos/core#> . @prefix vcard: <http://www.w3.org/2006/vcard/ns#> . @prefix xsd: <http://www.w3.org/2001/XMLSchema#> . <https://registrering.fellesdatakatalog.digdir.no/catalogs/971277882/datasets/29a2bf37-5867-4c90-bc74-5a8c4e118572> rdf:type dcat:Dataset ; dct:accessRights <http://publications.europa.eu/resource/authority/access-right/PUBLIC> ; dct:description \"Visning over all norsk offentlig bistand fra 1960 til siste kalenderår sortert etter partnerorganisasjoner.\"@nb ; dct:identifier \"https://registrering.fellesdatakatalog.digdir.no/catalogs/971277882/datasets/29a2bf37-5867-4c90-bc74-5a8c4e118572\" ; dct:language <http://publications.europa.eu/resource/authority/language/NOR> , <http://publications.europa.eu/resource/authority/language/ENG> ; dct:provenance <http://data.brreg.no/datakatalog/provinens/nasjonal> ; dct:publisher <https://organization-catalogue.fellesdatakatalog.digdir.no/organizations/971277882> ; dct:title \"Bistandsresultater - bistand etter partner\"@nb ; dct:type \"Data\" ; dcat:contactPoint [ rdf:type vcard:Organization ; vcard:hasEmail <mailto:resultater@norad.no> ] ; dcat:distribution [ rdf:type dcat:Distribution ; dct:description \"Norsk bistand i tall etter partner\"@nb ; dct:format <https://www.iana.org/assignments/media-types/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet> , <https://www.iana.org/assignments/media-types/text/csv> ; dct:license <http://data.norge.no/nlod/no/2.0> ; dct:title \"Bistandsresultater - bistand etter partner\"@nb ; dcat:accessURL <https://resultater.norad.no/partner/> ] ; dcat:keyword \"oda\"@nb , \"norad\"@nb , \"bistand\"@nb ; dcat:landingPage <https://resultater.norad.no/partner/> ; dcat:theme <http://publications.europa.eu/resource/authority/data-theme/INTR> ; dqv:hasQualityAnnotation [ rdf:type dqv:QualityAnnotation ; dqv:inDimension iso:Currentness ] ; prov:qualifiedAttribution [ rdf:type prov:Attribution ; dcat:hadRole <http://registry.it.csiro.au/def/isotc211/CI_RoleCode/contributor> ; prov:agent <https://data.brreg.no/enhetsregisteret/api/enheter/971277882> ] . <http://publications.europa.eu/resource/authority/language/ENG> rdf:type dct:LinguisticSystem ; <http://publications.europa.eu/ontology/authority/authority-code> \"ENG\" ; skos:prefLabel \"Engelsk\"@nb . <http://publications.europa.eu/resource/authority/language/NOR> rdf:type dct:LinguisticSystem ; <http://publications.europa.eu/ontology/authority/authority-code> \"NOR\" ; skos:prefLabel \"Norsk\"@nb ." }'

docker-compose exec schema-registry bash -c "echo '$messages'|kafka-avro-console-producer --bootstrap-server kafka:29092 --topic dataset-events --property value.schema.id=${schema_id}"
