package com.example.demo.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public record CreateUserRequestVo(
        @NotBlank(message = "Username is required and cannot be blank.")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters.")
        String username,

        @NotBlank(message = "Password is required.")
        @Size(min = 8, message = "Password must be at least 8 characters long.")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
        )
        String password,

        @NotBlank(message = "Email is required.")
        @Email(message = "Please provide a valid email address.")
        String email,

        @NotBlank(message = "First name is required")
        String firstName,
        @NotBlank(message = "Last name is required")
        String lastName
) {
}