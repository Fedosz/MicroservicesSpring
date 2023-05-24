package ru.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.auth.config.JwtService;
import ru.auth.models.User;
import ru.auth.repositories.UserRepo;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/info")
@RequiredArgsConstructor
public class InfoController {

    private final UserRepo repository;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<String> sayHello(@RequestHeader(name="Authorization") String token) {
        token = token.substring(7);

        String username = jwtService.extractUsername(token);
        User user = repository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("No such user in database"));

        String answer = "Username = " + user.getUsername_() + "\n" +
                "Email = " + user.getEmail() +
                "\nRole = " + user.getRole();


        return ResponseEntity.ok(answer);
    }
}
