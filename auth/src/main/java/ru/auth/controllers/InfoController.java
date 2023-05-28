package ru.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.auth.config.JwtService;
import ru.auth.models.User;
import ru.auth.repositories.UserRepo;

@RestController
@RequestMapping("/api/info")
@RequiredArgsConstructor
public class InfoController {

    private final UserRepo repository;
    private final JwtService jwtService;

    /**
     * information request
     * @param token = user token
     * @return = information
     */
    @GetMapping
    public ResponseEntity<String> Information(@RequestHeader(name="Authorization") String token) {
        token = token.substring(7);

        User user = null;

        String username = jwtService.extractUsername(token);
        try {
            user = repository.findByEmail(username).orElseThrow(
                    () -> new UsernameNotFoundException("No such user in database"));
        } catch (UsernameNotFoundException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }

        String answer = "Username = " + user.getUsername_() + "\n" +
                "Email = " + user.getEmail() +
                "\nRole = " + user.getRole();

        return ResponseEntity.ok(answer);
    }
}
