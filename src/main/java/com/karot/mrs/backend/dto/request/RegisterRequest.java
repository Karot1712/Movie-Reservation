package com.karot.mrs.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Email is required.")
    @Email(message = "Email should be valid.")
    private String email;

    private String firstName;
    private String lastName;
    private String gender;

    @NotBlank(message = "Password is required.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$",
            message = "Password should contain at least 1 uppercase, 1 lowercase and 1 digit")
    private String password;
    private String role;
}
