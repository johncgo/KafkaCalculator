import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Arrays;
import java.util.Properties;

public class CalculatorClient {
    public static void main(String[] args) {
        Properties consumerProperties = new Properties();
        consumerProperties.setProperty("bootstrap.servers", "localhost:9092");
        consumerProperties.setProperty("group.id", "calculator-group");
        consumerProperties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProperties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        Properties producerProperties = new Properties();
        producerProperties.setProperty("bootstrap.servers", "localhost:9092");
        producerProperties.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProperties.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(consumerProperties);
        consumer.subscribe(Arrays.asList("calculator-results"));

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties);

        String operation = "addition";
        double firstOperand = 1;
        double secondOperand = 2;

        String request = operation + "," + firstOperand + "," + secondOperand;
        producer.send(new ProducerRecord<String, String>("calculator-requests", "request-key", request));

        ConsumerRecords<String, String> records = consumer.poll(100);
        for (ConsumerRecord<String, String> record : records) {
            System.out.println("Result of the operation: " + record.value());
        }
    }
}
