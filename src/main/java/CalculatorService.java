import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Arrays;
import java.util.Properties;

public class CalculatorService {
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
        consumer.subscribe(Arrays.asList("calculator-requests"));

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties);

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                String[] elements = record.value().split(",");
                String operation = elements[0];
                double firstOperand = Double.parseDouble(elements[1]);
                double secondOperand = Double.parseDouble(elements[2]);

                double result = 0;
                if ("addition".equals(operation)) {
                    result = firstOperand + secondOperand;
                } else if ("subtraction".equals(operation)) {
                    result = firstOperand - secondOperand;
                } else if ("multiplication".equals(operation)) {
                    result = firstOperand * secondOperand;
                } else if ("division".equals(operation)) {
                    result = firstOperand / secondOperand;
                } else if ("natural logarithm".equals(operation)) {
                    result = Math.log(firstOperand);
                } else if ("logarithm base 10".equals(operation)) {
                    result = Math.log10(firstOperand);
                } else if ("exponentiation".equals(operation)) {
                    result = Math.pow(firstOperand, secondOperand);
                } else if ("sine".equals(operation)) {
                    result = Math.sin(firstOperand);
                } else if ("cosine".equals(operation)) {
                    result = Math.cos(firstOperand);
                } else if ("square root".equals(operation)) {
                    result = Math.sqrt(
                            firstOperand);
                } else {
                    System.out.println("Invalid operation");
                }
                producer.send(new ProducerRecord<String, String>("calculator-results", record.key(), Double.toString(result)));
            }
        }
    }
}