package ru.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.auth.dao.UserDAO;
import ru.auth.models.LoginForm;
import ru.auth.models.RegisterForm;
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

    @PostMapping("/register")
    public String saveEmployee(@RequestBody RegisterForm registerForm) {
        //validate input
        userDAO.save(registerForm);
        return registerForm.getUsername() + registerForm.getEmail() + registerForm.getPassword();
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginForm loginForm) {
        return userDAO.login(loginForm);
    }

    @GetMapping("/getAll")
    public List<User> getAll() {
        return userDAO.index();
    }

    @GetMapping("/getUser/{id}")
    public User getUser(@PathVariable int id) {
        return null;
    }

}
