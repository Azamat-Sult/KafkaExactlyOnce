package ru.example.consumer1.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "persons")
@NoArgsConstructor
public class PersonEntity {

    public PersonEntity(String kafkaKey, String firstName, String lastName) {
        this.kafkaKey = kafkaKey;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String kafkaKey;
    private String firstName;
    private String lastName;
}