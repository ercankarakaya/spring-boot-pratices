package com.ercan.controller;

import com.ercan.entity.Person;
import com.ercan.response.Response;
import com.ercan.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPersonById(@PathVariable Long id) {
        Person person = personService.getPersonById(id);
        return ResponseEntity.ok(
                Response.<Person>builder()
                        .timeStamp(LocalDateTime.now())
                        .statusCode(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.ACCEPTED)
                        .message("Person: " + id)
                        .data(person)
                        .build());
    }

    @GetMapping
    public ResponseEntity<?> getAllPersons() {
        List<Person> personList = personService.getAllPersons();
        return ResponseEntity.ok(
                Response.<List<Person>>builder()
                        .timeStamp(LocalDateTime.now())
                        .statusCode(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.ACCEPTED)
                        .message("Persons listed.")
                        .data(personList)
                        .build());
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid Person person) {
        Person personSaved = personService.save(person);
        return ResponseEntity.ok(
                Response.<Person>builder()
                        .timeStamp(LocalDateTime.now())
                        .statusCode(HttpStatus.CREATED.value())
                        .httpStatus(HttpStatus.CREATED)
                        .message("Person saved.")
                        .data(personSaved)
                        .build());
    }

}
