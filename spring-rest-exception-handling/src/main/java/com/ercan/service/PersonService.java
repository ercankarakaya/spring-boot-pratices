package com.ercan.service;

import com.ercan.exception.ApplicationException;
import com.ercan.model.Person;
import com.ercan.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;


    public Person save(Person person) {
        return personRepository.save(person);
    }

    public Person getPersonById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() ->
                        new ApplicationException(
                                "PERSON_NOT_FOUND",
                                String.format("Person with id=%d not found!", id),
                                HttpStatus.NOT_FOUND));
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }


    /*** Without Database Connection

     private Map<Long, Person> persons = new ConcurrentHashMap<>() {
     {
     put(1L, new Person(1L, "Micheal", "Jackson"));
     put(2L, new Person(2L, "Nelson", "Mandela"));
     put(3L, new Person(3L, "John", "Wick"));
     }
     };

     public List<Person> getAllPersons() {
     return persons.values().stream().collect(Collectors.toUnmodifiableList());
     }


     public Person getPersonById(Long id) {
     var person = persons.get(id);
     return Optional.ofNullable(person)
     .orElseThrow(() ->
     new ApplicationException(
     "PERSON_NOT_FOUND",
     String.format("Person with id=%d not found!", id),
     HttpStatus.NOT_FOUND)
     );
     }
     */


}
