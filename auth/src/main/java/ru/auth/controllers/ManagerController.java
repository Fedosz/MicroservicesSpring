package ru.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.auth.config.JwtService;
import ru.auth.models.Role;
import ru.auth.models.User;
import ru.auth.models.request.ManagerRequest;
import ru.auth.repositories.UserRepo;

@RestController
@RequestMapping("/api/manager")
@RequiredArgsConstructor
public class ManagerController {
    private final UserRepo repository;

    @PostMapping("/changeRole")
    public ResponseEntity<String> Information(@RequestBody ManagerRequest request) {
        Role role;
        switch (request.getRole()) {
            case "customer":
                role = Role.customer;
                break;
            case "chef":
                role = Role.chef;
                break;
            case "manager":
                role = Role.manager;
                break;
            default:
                return new ResponseEntity<>("Wrong role", HttpStatus.BAD_REQUEST);
        }

        String email = request.getEmail();
        User user = null;
        try {
        user = repository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Username not found")
        );
        } catch (UsernameNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

        user.setRole(role);

        repository.save(user);

        return ResponseEntity.ok("Role successfully changed");
    }
}
