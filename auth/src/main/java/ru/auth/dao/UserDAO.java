package ru.auth.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.auth.models.User;
import org.apache.commons.codec.digest.DigestUtils;
import java.util.List;

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

    public void save(User user) {
        String password_hash = MD5Hash(user.getPassword_hash());
        jdbcTemplate.update("INSERT INTO USERS (username, email, password_hash, role) VALUES(?, ?, ?, ?)", user.getUsername(),
                user.getEmail(), password_hash ,user.getRole());
    }

    private String MD5Hash(String line) {
        return DigestUtils
                .md5Hex(line).toUpperCase();
    }
}
