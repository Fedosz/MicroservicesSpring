package ru.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import ru.auth.dao.UserDAO;
import ru.auth.models.User;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserDAO userDAO;

    @Autowired
    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @PostMapping("/addPerson")
    public String saveEmployee(@RequestBody User user) {
        //validate input
        userDAO.save(user);
        return "User saved successfully";
    }

    @GetMapping("/getAll")
    public List<User> getAll() {
        return userDAO.index();
    }

}
