package com.ead.auth_service.dtos;

import jakarta.validation.constraints.NotBlank;

public record JwtRecordDto(@NotBlank String token,
                           String type) {
    public JwtRecordDto(@NotBlank String token) {
        this(token, "Bearer");
    }
}
