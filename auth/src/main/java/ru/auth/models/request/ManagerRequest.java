package ru.auth.models.request;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.auth.models.Role;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerRequest {
    private String email;
    private String role;
}
