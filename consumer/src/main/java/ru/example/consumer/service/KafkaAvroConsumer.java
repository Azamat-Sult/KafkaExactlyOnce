package ru.example.consumer.service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.example.consumer.avro.schemas.Person;

@Service
@RequiredArgsConstructor
public class KafkaAvroConsumer {

    @KafkaListener(
            topics = "${kafka.topics.person-topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory",
            concurrency = "1"
    )
    public void consume(ConsumerRecord<String, Person> topicMsg) {
        String msgKey = topicMsg.key();
        Person person = topicMsg.value();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("Key: " + msgKey + ", Value: " + person);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }
}