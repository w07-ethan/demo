package com.example.demo.dto.response.user;

import java.time.Instant;

public record UserResponse(
        String id,
        String username,
        String email,
        String firstName,
        String lastName
) {
}