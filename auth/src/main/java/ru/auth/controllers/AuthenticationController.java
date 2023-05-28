package ru.auth.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.auth.dao.AuthenticationService;
import ru.auth.models.request.AuthRequest;
import ru.auth.models.AuthenticationResponse;
import ru.auth.models.request.RegisterRequest;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    /**
     * registration request
     * @param request = request body
     * @param bindingResult = @Valid results
     * @return = Answer
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            String response = "";
            for (FieldError error : fieldErrorList) {
                response += error.getDefaultMessage() + "\n";
            }
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(service.register(request));
    }

    /**
     * login request
     * @param request = request body
     * @return = answer
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
