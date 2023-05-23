package ru.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.auth.dao.UserDAO;
import ru.auth.models.RegisterForm;
import ru.auth.models.User;
import ru.auth.repositories.UserRepo;

import java.util.List;

@RestController
public class UserController {

    private final UserRepo userRepo;
    private final UserDAO userDAO;

    @Autowired
    public UserController(UserRepo userRepo, UserDAO userDAO) {
        this.userRepo = userRepo;
        this.userDAO = userDAO;
    }

    @PostMapping("/register")
    public String saveEmployee(@RequestBody RegisterForm registerForm) {
        //validate input
        userDAO.save(registerForm);
        return "something";
    }

    @GetMapping("/getAll")
    public Iterable<User> getAll() {

        return userRepo.findAll();
    }

    @GetMapping("/getUser/{id}")
    public User getUser(@PathVariable int id) {
        return null;
    }

}
