package ru.example.consumer1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.consumer.avro.schemas.Person;
import ru.example.consumer1.entity.PersonEntity;
import ru.example.consumer1.repository.PersonRepository;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public void create(String kafkaKey, Person person) {
        PersonEntity personEntity = new PersonEntity(kafkaKey, person.getFirstName().toString(), person.getLastName().toString());
        personRepository.save(personEntity);
    }
}