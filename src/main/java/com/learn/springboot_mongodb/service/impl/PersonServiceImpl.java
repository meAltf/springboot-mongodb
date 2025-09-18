package com.learn.springboot_mongodb.service.impl;

import com.learn.springboot_mongodb.collection.Person;
import com.learn.springboot_mongodb.repository.PersonRepository;
import com.learn.springboot_mongodb.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public String save(Person person) {
        return personRepository.save(person).getPersonId();
    }
}
