package com.ead.notificationservice.services;

import com.ead.notificationservice.dtos.NotificationRecordCommandDto;
import com.ead.notificationservice.models.NotificationModel;

public interface NotificationService {
    NotificationModel saveNotification(NotificationRecordCommandDto notificationRecordCommandDto);
}
