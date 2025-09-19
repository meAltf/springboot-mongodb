package com.learn.springboot_mongodb.service;

import com.learn.springboot_mongodb.collection.Person;

import java.util.List;

public interface PersonService {

    String save(Person person);

    List<Person> getPersonStartWith(String name);

    String deleteById(String id);

    List<Person> getPersonByAge(Integer minAge, Integer maxAge);
}
