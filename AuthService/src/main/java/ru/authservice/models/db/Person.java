package ru.authservice.models.db;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "user")
@Data
public class Person {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "username", unique = true)
    @Size(min = 2, max = 30, message = "Username should be between 2 and 30 characters")
    private String username;

    @Email(message = "Enter valid email")
    private String email;
    private String password_hash;
    private String role;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Person() {}
}
