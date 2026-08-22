package com.vellora.vellora_backend.dto;

public record AuthResponse(UserSummary user, String token) {}