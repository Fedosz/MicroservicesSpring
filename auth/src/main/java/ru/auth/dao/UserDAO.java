package ru.auth.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.auth.models.LoginForm;
import ru.auth.models.RegisterForm;
import ru.auth.models.User;
import org.apache.commons.codec.digest.DigestUtils;
import java.util.List;
import java.util.Objects;

@Component
public class UserDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> index() {
        return jdbcTemplate.query("SELECT * FROM USERS", new BeanPropertyRowMapper<>(User.class));
    }

    public void save(RegisterForm registerForm) {
        String password_hash = MD5Hash(registerForm.getPassword());
        jdbcTemplate.update("INSERT INTO USERS (username, email, password_hash, role) VALUES(?, ?, ?, ?)", registerForm.getUsername(),
                registerForm.getEmail(), password_hash, "customer");
    }

    public String login(LoginForm loginForm) {
        String password_hash = MD5Hash(loginForm.getPassword());
        User user = jdbcTemplate.query("SELECT * FROM USERS WHERE email=?",
                new Object[]{loginForm.getEmail()},
                new BeanPropertyRowMapper<>(User.class)).stream().findAny().orElse(null);
        if (user == null) {
            return "User with email " + loginForm.getEmail() + " doesn't exist";
        } else {
            if (Objects.equals(user.getPassword_hash(), password_hash)) {
                //create session
                return "Login succeeded";
            } else {
                return "Wrong password";
            }
        }
    }

    private String MD5Hash(String line) {
        return DigestUtils
                .md5Hex(line).toUpperCase();
    }
}
