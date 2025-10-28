package com.example.demo.dto.response;

import java.time.LocalDateTime;

public record UserVo(
        String id,
        String username,
        String email,
        String fullName,
        boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}