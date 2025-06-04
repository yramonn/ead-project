package com.ead.notificationservice.services;

import com.ead.notificationservice.dtos.NotificationRecordCommandDto;
import com.ead.notificationservice.dtos.NotificationRecordDto;
import com.ead.notificationservice.models.NotificationModel;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface NotificationService {
    NotificationModel saveNotification(NotificationRecordCommandDto notificationRecordCommandDto);
    Page<NotificationModel> findAllNotificationsByUser(UUID userId, Pageable pageable);
    Optional<NotificationModel> findByNotificationIdAndUserId(UUID notificationId, UUID userId);

    NotificationModel updateNotification(NotificationRecordDto notificationRecordDto, NotificationModel notificationModel);
}
