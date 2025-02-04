package ru.example.producer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.example.producer.service.KafkaAvroProducer;
import ru.example.producer.avro.schemas.Person;

@RestController
@RequiredArgsConstructor
public class AvroController {

    private final KafkaAvroProducer producer;

    @PostMapping("/person")
    public String sendPerson(@RequestHeader("msgKey") String msgKey, @RequestBody Person person) {
        producer.send(msgKey, person);
        return "Info about person published";
    }

    @GetMapping("/generateBatchOfPersons/{batchSize}")
    public String sendPerson(@PathVariable("batchSize") int batchSize) {
        producer.sendBatch(batchSize);
        return "Batch of persons generated and published";
    }
}