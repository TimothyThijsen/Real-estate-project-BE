package nl.fontys.realestateproject.business.dto.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest {
    @Email
    @NotBlank
    @Size(max = 50, message = "Email must be at most 50 characters long")
    private String email;

    @NotBlank
    @Size(min = 2, message = "First name must be at least 2 characters long")
    @Size(max = 20, message = "First name must be at most 20 characters long")
    private String firstName;

    @NotBlank
    @Size(min = 2, message = "Last name must be at least 2 characters long")
    @Size(max = 20, message = "Last name must be at most 20 characters long")
    private String lastName;

    @NotBlank
    @Size(min = 5, message = "Password must be at least 5 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one uppercase letter, one lowercase letter and one number")
    private String password;

    @NotBlank
    private String role;
}

