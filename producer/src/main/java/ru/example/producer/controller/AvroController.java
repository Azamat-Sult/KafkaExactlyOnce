package ru.example.producer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.example.producer.service.KafkaAvroProducer;
import ru.example.producer.avro.schemas.Person;
import ru.example.producer.avro.schemas.Car;

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
    public String sendBatch(@PathVariable("batchSize") int batchSize) {
        producer.sendBatch(batchSize);
        return "Batch of persons generated and published";
    }

    @PostMapping("/car")
    public String sendCar(@RequestHeader("msgKey") String msgKey, @RequestBody Car car) {
        producer.send(msgKey, car);
        return "Info about car published";
    }
}