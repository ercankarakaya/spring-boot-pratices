package com.ercan.service;

import com.ercan.model.Person;
import com.ercan.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PersonServiceImpl implements PersonService{

    private static final Logger logger = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    private PersonRepository personRepository;


    @PostConstruct
    public void init(){
        logger.info("PersonServiceImpl.init() method called.");

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
        logger.info("PersonServiceImpl.getPersonAll() method called.");
        return personRepository.findAll();
    }
}
