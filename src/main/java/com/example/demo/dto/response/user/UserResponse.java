package com.example.demo.dto.response.user;

import java.time.LocalDateTime;

public record UserResponse(
        String id,
        String username,
        String email,
        String firstName,
        String lastName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}