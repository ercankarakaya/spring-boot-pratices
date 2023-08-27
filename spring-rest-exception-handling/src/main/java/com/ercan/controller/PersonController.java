package com.ercan.controller;

import com.ercan.model.Person;
import com.ercan.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPersonById(@PathVariable Long id) {
        return ResponseEntity.ok(personService
                .getPersonById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAllPersons() {
        return ResponseEntity.ok(personService.getAllPersons());
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid Person person){
        return ResponseEntity.ok(personService.save(person));
    }

}
