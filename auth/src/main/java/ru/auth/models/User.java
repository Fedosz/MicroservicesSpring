package ru.auth.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "id", insertable = false)
    private int id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password_hash", nullable = false)
    private String password_hash;
    @Column(name = "role", insertable = false)
    private String role;
    @Column(name = "created_at", insertable = false)
    private Timestamp created_at;
    @Column(name = "updated_at", insertable = false)
    private Timestamp updated_at;

}
