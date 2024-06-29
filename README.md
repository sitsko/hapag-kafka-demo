# Quarkus - Kafka Serialization and Deserialization Demo

This repository contains three variants of the same system:
1. A variant using Avro to encode the Kafka records

## The system

The system is composed of two applications:

1. A producer writing _Container_ into the `containers` Kafka topic.
2. A consumer consuming these _Container_ .

## Building

Run `mvn install` from the root.

## Running the demo

Select a variant and open two terminals.
In the first one, go to the producer module of the variant and run `mvn quarkus:dev`.
In the second one, go to the consumer module of the variant and run `mvn quarkus:dev`.

For example, to run the Avro variant:

1. in the first terminal, run `mvn quarkus:dev` from the `avro-hapag-consumer`.
2. in the second terminal, run `mvn quarkus:dev` from the `avro-hapag-producer`.