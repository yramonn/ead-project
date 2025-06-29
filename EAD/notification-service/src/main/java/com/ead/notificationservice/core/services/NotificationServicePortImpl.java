package com.ead.notificationservice.core.services;

import com.ead.notificationservice.core.domain.NotificationDomain;
import com.ead.notificationservice.core.domain.PageInfo;
import com.ead.notificationservice.core.domain.enums.NotificationStatus;
import com.ead.notificationservice.core.ports.NotificationPersistencePort;
import com.ead.notificationservice.core.ports.NotificationServicePort;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class NotificationServicePortImpl implements NotificationServicePort {

    private final NotificationPersistencePort notificationPersistencePort;

    public NotificationServicePortImpl(NotificationPersistencePort notificationPersistencePort) {
        this.notificationPersistencePort = notificationPersistencePort;
    }

    @Override
    public NotificationDomain saveNotification(NotificationDomain notificationDomain) {
       notificationDomain.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
       notificationDomain.setNotificationStatus(NotificationStatus.CREATED);
       return notificationPersistencePort.saveNotification(notificationDomain);
    }

    @Override
    public List<NotificationDomain> findAllNotificationsByUser(UUID userId, PageInfo pageInfo) {
        return List.of();
    }

    @Override
    public Optional<NotificationDomain> findByNotificationIdAndUserId(UUID notificationId, UUID userId) {
        return Optional.empty();
    }

    @Override
    public NotificationDomain updateNotification(NotificationStatus notificationRecordDto, NotificationDomain notificationDomain) {
        return null;
    }
}
