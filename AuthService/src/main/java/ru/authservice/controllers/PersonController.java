package ru.authservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.authservice.models.db.Person;
import ru.authservice.dao.PersonRep;

import java.util.List;

@RestController
public class PersonController {

    private PersonRep personRep;

    @Autowired
    public PersonController(PersonRep personRep) {
        this.personRep = personRep;
    }

    @PostMapping("/savePerson")
    public String savePerson(@RequestBody Person person) {
        personRep.save(person);
        return "Employee saved";
    }

    @GetMapping("/getPeople")
    public List<Person> getAll() {
        return personRep.findAll();
    }

}
