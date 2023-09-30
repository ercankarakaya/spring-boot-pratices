package com.ercan.service;

import com.ercan.model.Person;
import com.ercan.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PersonServiceImpl implements PersonService{

    @Autowired
    private PersonRepository personRepository;


    @PostConstruct
    public void init(){
        List<Person> personList = Stream.of
                (
                new Person("Micheal","Jackson"),
                new Person("John","Wick"),
                new Person("Walter","White")
                ).collect(Collectors.toList());

        personRepository.saveAll(personList);
    }

    @Override
    public List<Person> getPersonAll(){
        return personRepository.findAll();
    }
}
