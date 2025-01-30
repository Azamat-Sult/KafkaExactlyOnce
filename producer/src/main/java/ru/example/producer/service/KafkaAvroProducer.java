package ru.example.producer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.example.producer.avro.schemas.Person;

@Service
@RequiredArgsConstructor
public class KafkaAvroProducer {

    @Value("${kafka.topics.person-topic}")
    private String topicName;

    private final KafkaTemplate<String, Person> kafkaTemplate;

    public void send(String msgKey, Person person) {
        kafkaTemplate.send(topicName, msgKey, person);
    }
}