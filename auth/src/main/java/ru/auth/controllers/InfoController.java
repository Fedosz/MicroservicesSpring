package ru.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.auth.models.User;
import ru.auth.repositories.UserRepo;

import java.util.List;

@RestController
@RequestMapping("/api/info")
@RequiredArgsConstructor
public class InfoController {

    private final UserRepo repository;

    @GetMapping("/all")
    public ResponseEntity<List<User>> sayHello() {
        return ResponseEntity.ok(repository.findAll());
    }
}
