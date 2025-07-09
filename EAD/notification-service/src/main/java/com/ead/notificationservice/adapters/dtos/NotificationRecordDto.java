package com.ead.notificationservice.adapters.dtos;

import com.ead.notificationservice.core.domain.enums.NotificationStatus;
import jakarta.validation.constraints.NotNull;

public record NotificationRecordDto(@NotNull NotificationStatus notificationStatus) {
}
