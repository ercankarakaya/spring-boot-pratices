package com.ercan.service;

import com.ercan.exception.ApplicationException;
import com.ercan.model.Person;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final Map<Long, Person> persons = new ConcurrentHashMap<>(){
        {
            put(1L,new Person(1L,"Micheal","Jackson"));
            put(2L,new Person(2L,"Nelson","Mandela"));
            put(3L,new Person(3L,"John","Wick"));
        }
    };

    public Person getPersonById(Long id){
        var person = persons.get(id);
        return Optional.of(person)
                .orElseThrow(()->new ApplicationException(
                        "PERSON_NOT_FOUND",
                        String.format("Person with id=%d not found!",id),
                        HttpStatus.NOT_FOUND)
                );
    }

    public List<Person> getAllPersons(){
        return persons.values().stream().collect(Collectors.toUnmodifiableList());
    }
}
