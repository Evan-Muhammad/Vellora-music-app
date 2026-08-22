package com.vellora.vellora_backend.dto;

import com.vellora.vellora_backend.model.Role;
import com.vellora.vellora_backend.model.User;

import java.time.LocalDateTime;

public record UserSummary(
        Long id,
        String username,
        String email,
        Role role,
        LocalDateTime createdAt
) {
    public static UserSummary from(User user) {
        return new UserSummary(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
}