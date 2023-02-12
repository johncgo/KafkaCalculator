# KafkaCalculator
Using a Message Oriented Middleware (MOM), such as Kafka, develop an asynchronously distributed scientific calculator with the functions listed below.

- Sum
- Subtraction
- Multiplication
- Division
- Logarithm base e (natural logarithm)
- Logarithm to base 10
- Exponentiation
- Sine
- Cosine
- Square root

The calculator must have at least two entities, the client and the server.


# Command
docker run -it --rm --name kafka-instance -p 9092:9092 -e KAFKA_ADVERTISED_HOST_NAME=127.0.0.1 -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 -e KAFKA_ADVERTISED_PORT=9092 --link zookeeper:zookeeper debezium/kafka:1.2

