FROM openjdk:8-jre-alpine

ARG KAFKA_VERSION=2.8.0
ARG SCALA_VERSION=2.13

RUN apk add --no-cache wget && \
    wget https://downloads.apache.org/kafka/${KAFKA_VERSION}/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz && \
    tar -xzf kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz && \
    rm kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz && \
    mv kafka_${SCALA_VERSION}-${KAFKA_VERSION} /kafka

WORKDIR /kafka

EXPOSE 9092

CMD [ "bin/kafka-server-start.sh", "config/server.properties" ]
