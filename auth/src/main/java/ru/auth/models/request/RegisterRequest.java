package ru.auth.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @Size(min = 2, max = 30, message = "Username should be between 2 and 30 characters")
    private String username;
    @Email(message = "Enter valid email")
    @NotEmpty(message = "Email must be not empty")
    private String email;
    @Size(min = 4, max = 16, message = "Password must contain 4-14 characters")
    private String password;
}
