package com.ead.notificationservice.dtos;

import com.ead.notificationservice.enums.NotificationStatus;
import jakarta.validation.constraints.NotNull;

public record NotificationRecordDto(@NotNull NotificationStatus notificationStatus) {
}
