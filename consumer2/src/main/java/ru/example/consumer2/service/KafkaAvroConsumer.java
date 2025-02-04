package ru.example.consumer2.service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import ru.example.consumer.avro.schemas.Person;

@Service
@RequiredArgsConstructor
public class KafkaAvroConsumer {

    private final PersonService personService;

    @KafkaListener(
            id = "fetchPersons",
            topics = "${kafka.topics.person-topic}",
            containerFactory = "personKafkaListenerContainerFactory",
            autoStartup = "${kafka.listeners.fetchPersons.enable}"
    )
    public void consume(ConsumerRecord<String, Person> personRecord, Acknowledgment acknowledgment) {

        String msgKey = personRecord.key();
        Person person = personRecord.value();

        while (true) {
            try {
                waitSomeTime(5);
                personService.create(msgKey, person);

                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                System.out.println("Key: " + msgKey + ", Value: " + person);
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

                acknowledgment.acknowledge();
                break;
            } catch (Exception e) {
                System.out.println("Error while trying to save person info...");
                waitSomeTime(5);
            }
        }
    }

    private void waitSomeTime(long secondsToSleep) {
        try {
            Thread.sleep(secondsToSleep * 1000);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}