package ru.auth.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.auth.models.RegisterForm;
import org.apache.commons.codec.digest.DigestUtils;

@Component
public class UserDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(RegisterForm registerForm) {
        String password_hash = MD5Hash(registerForm.getPassword());
        jdbcTemplate.update("INSERT INTO USERS (username, email, password_hash, role) VALUES(?, ?, ?, ?)", registerForm.getUsername(),
                registerForm.getEmail(), password_hash, "customer");
    }

    private String MD5Hash(String line) {
        return DigestUtils
                .md5Hex(line).toUpperCase();
    }
}
