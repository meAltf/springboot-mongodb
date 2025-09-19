package com.learn.springboot_mongodb.service;

import com.learn.springboot_mongodb.collection.Person;

import java.util.List;

public interface PersonService {

    String save(Person person);

    List<Person> getPersonStartWith(String name);
}
