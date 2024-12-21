package com.ead.courseservice.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SubscriptionRecordDto(@NotNull(message = "UserId is Mandatory")
                                    UUID userId) {
}
