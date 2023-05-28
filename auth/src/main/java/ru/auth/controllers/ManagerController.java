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

    /**
     * change role request
     * @param request = request body
     * @return answer
     */
    @PutMapping("/changeRole")
    public ResponseEntity<String> changeRole(@RequestBody ManagerRequest request) {
        // Check role
        Role role;
        switch (request.getRole()) {
            case "customer" -> role = Role.customer;
            case "chef" -> role = Role.chef;
            case "manager" -> role = Role.manager;
            default -> {
                return new ResponseEntity<>("Wrong role", HttpStatus.BAD_REQUEST);
            }
        }

        //Find user in database
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
        //save changed
        repository.save(user);

        return ResponseEntity.ok("Role successfully changed");
    }
}
