package ru.auth.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.auth.models.*;
import ru.auth.config.JwtService;
import ru.auth.models.request.AuthRequest;
import ru.auth.models.request.RegisterRequest;
import ru.auth.repositories.SessionRepository;
import ru.auth.repositories.UserRepo;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final SessionRepository sessionRepository;

    public String register(RegisterRequest request) {
        var user = User.builder()
                .email(request.getEmail())
                .username_(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.customer)
                .build();

        repository.save(user);

        return "Registration completed successfully";
    }

    public AuthenticationResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var jwtToken = jwtService.generateToken(user);

        AddSessionToDB(jwtToken);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void AddSessionToDB(String token) {
        Session session = new Session();
        session.setSession_token(token);

        String username = jwtService.extractUsername(token);
        var user = repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        session.setUser_id(user.getId());
        Timestamp date = new Timestamp(jwtService.extractExpiration(token).getTime());
        session.setExpires_at(date);

        sessionRepository.save(session);
    }
}
