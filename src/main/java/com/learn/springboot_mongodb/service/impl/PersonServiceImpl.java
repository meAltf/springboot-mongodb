package com.learn.springboot_mongodb.service.impl;

import com.learn.springboot_mongodb.collection.Person;
import com.learn.springboot_mongodb.repository.PersonRepository;
import com.learn.springboot_mongodb.service.PersonService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
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

    @Override
    public List<Document> getOldestPersonByCity() {
        UnwindOperation unwindOperation = Aggregation.unwind("addresses");
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "age");
        GroupOperation groupOperation = Aggregation.group("addresses.city")
                .first(Aggregation.ROOT)
                .as("OldestPersonInCity");

        Aggregation aggregation = Aggregation.newAggregation(unwindOperation, sortOperation, groupOperation);

        List<Document> Oldestperson = mongoTemplate.aggregate(aggregation, Person.class, Document.class).getMappedResults();
        return Oldestperson;
    }

    @Override
    public List<Document> getPopulationByCity() {
        UnwindOperation unwindOperation = Aggregation.unwind("addresses");
        GroupOperation groupOperation = Aggregation.group("addresses.city").count().as("populationCount");
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "populationCount");

        ProjectionOperation projectionOperation = Aggregation.project()
                .andExpression("_id").as("city")
                .andExpression("populationCount").as("count")
                .andExclude("_id");

        Aggregation aggregation = Aggregation.newAggregation(unwindOperation, groupOperation, sortOperation, projectionOperation);

        List<Document> documentList = mongoTemplate.aggregate(aggregation, Person.class, Document.class).getMappedResults();

        return documentList;

    }
}
