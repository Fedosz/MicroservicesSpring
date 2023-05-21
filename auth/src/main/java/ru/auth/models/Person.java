package ru.auth.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "people")
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "role", insertable = false)
    private String role;
    @Column(name = "created_at", insertable = false)
    private Timestamp created_at;
    @Column(name = "updated_at", insertable = false)
    private Timestamp updated_at;

}
