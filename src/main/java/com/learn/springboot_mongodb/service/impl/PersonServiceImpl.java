package com.learn.springboot_mongodb.service.impl;

import com.learn.springboot_mongodb.collection.Person;
import com.learn.springboot_mongodb.repository.PersonRepository;
import com.learn.springboot_mongodb.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String save(Person person) {
        return personRepository.save(person).getPersonId();
    }

    @Override
    public List<Person> getPersonStartWith(String name) {
        return personRepository.findByFirstNameStartsWith(name);
    }

    @Override
    public String deleteById(String id) {
        personRepository.deleteById(id);
        return id + " has been deleted successfully";
    }

    @Override
    public List<Person> getPersonByAge(Integer minAge, Integer maxAge) {
        return personRepository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    public Page<Person> search(String name, Integer minAge, Integer maxAge, String city, Pageable pageable) {

        Query query = new Query().with(pageable);
        List<Criteria> criteriaList = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            criteriaList.add(Criteria.where("firstName").regex(name, "i"));
        }

        if (minAge != null && maxAge != null) {
            criteriaList.add(Criteria.where("age").gte(minAge).lte(maxAge));
        }

        if (city != null && !city.isEmpty()) {
            criteriaList.add(Criteria.where("addresses.city").is(city));
        }

        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria()
                    .andOperator(criteriaList.toArray(new Criteria[0])));
        }

        Page<Person> personListPage = PageableExecutionUtils.getPage(
                mongoTemplate.find(query, Person.class
                ), pageable, () -> mongoTemplate.count(query.skip(0).limit(0), Person.class)
        );
        return personListPage;
    }
}
