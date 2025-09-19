package com.learn.springboot_mongodb.controller;

import com.learn.springboot_mongodb.collection.Person;
import com.learn.springboot_mongodb.service.PersonService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/save")
    public String save(@RequestBody Person person) {
        return personService.save(person);
    }

    @GetMapping("/getPersonByName/{name}")
    public List<Person> getPersonStartWith(@RequestParam("name") String name) {
        return personService.getPersonStartWith(name);
    }

    @DeleteMapping("/deleteById/{id}")
    public String deleteById(@PathVariable String id) {
        return personService.deleteById(id);
    }

    @GetMapping("/getPersonByAge")
    public List<Person> getPersonByAge(@RequestParam Integer minAge,
                                       @RequestParam Integer maxAge) {
        return personService.getPersonByAge(minAge, maxAge);
    }

    @GetMapping("/search")
    public Page<Person> searchPerson(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        return personService.search(name, minAge, maxAge, city, pageable);
    }

    @GetMapping("/oldestPerson")
    public List<Document> getOldestPerson() {
        return personService.getOldestPersonByCity();
    }
}
