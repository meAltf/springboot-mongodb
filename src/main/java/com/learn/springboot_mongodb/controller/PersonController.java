package com.learn.springboot_mongodb.controller;

import com.learn.springboot_mongodb.collection.Person;
import com.learn.springboot_mongodb.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
