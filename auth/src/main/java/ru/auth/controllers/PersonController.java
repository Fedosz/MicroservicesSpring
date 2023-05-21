package ru.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import ru.auth.dao.PersonRepository;
import ru.auth.models.Person;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class PersonController {

    @Autowired
    private PersonRepository repository;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/savePerson")
    public String saveEmployee(@RequestBody Person person) {

        // Current time
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        person.setCreated_at(timestamp);
        person.setUpdated_at(timestamp);

        try {
            repository.save(person);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "Person saved";
    }

    @GetMapping("/getAll")
    public List<Person> getAll() {
        return repository.findAll();
    }

    @GetMapping("/getPerson/{id}")
    public Optional<Person> getEmployeeById(@PathVariable Integer id) {
        return repository.findById(id);
    }

}
