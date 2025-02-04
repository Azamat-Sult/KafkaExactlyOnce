package ru.example.consumer3.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

    @GetMapping("/healthcheck")
    public String sendPerson() {
        return "I am alive! :)";
    }
}