package com.ead.notificationservice.core.ports;

import com.ead.notificationservice.core.domain.NotificationDomain;
import com.ead.notificationservice.core.domain.PageInfo;
import com.ead.notificationservice.core.domain.enums.NotificationStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationPersistencePort {

    NotificationDomain saveNotification(NotificationDomain notificationDomain);
    List<NotificationDomain> findAllNotificationsByUserIdAndNotificationStatus(UUID userId,NotificationStatus notificationStatus,  PageInfo pageInfo);
    Optional<NotificationDomain> findByNotificationIdAndUserId(UUID notificationId, UUID userId);
    NotificationDomain updateNotification(NotificationStatus notificationStatus, NotificationDomain notificationDomain);
}
