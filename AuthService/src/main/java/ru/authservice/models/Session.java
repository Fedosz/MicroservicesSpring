package ru.authservice.models;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Session {
    private int id;
    private int user_id;
    private String session_token;
    private Timestamp expires_at;
}
