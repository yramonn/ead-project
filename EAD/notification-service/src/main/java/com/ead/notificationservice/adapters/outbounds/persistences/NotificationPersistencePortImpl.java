package com.ead.notificationservice.adapters.outbounds.persistences;

import com.ead.notificationservice.adapters.outbounds.entities.NotificationEntity;
import com.ead.notificationservice.core.domain.NotificationDomain;
import com.ead.notificationservice.core.domain.PageInfo;
import com.ead.notificationservice.core.domain.enums.NotificationStatus;
import com.ead.notificationservice.core.ports.NotificationPersistencePort;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class NotificationPersistencePortImpl implements NotificationPersistencePort {

    private final NotificationJpaRepository notificationJpaRepository;
    private final ModelMapper modelMapper;

    public NotificationPersistencePortImpl(NotificationJpaRepository notificationJpaRepository, ModelMapper modelMapper) {
        this.notificationJpaRepository = notificationJpaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public NotificationDomain saveNotification(NotificationDomain notificationDomain) {
        var notificationEntity = notificationJpaRepository.save(modelMapper.map(notificationDomain, NotificationEntity.class));
        return modelMapper.map(notificationEntity, NotificationDomain.class);
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
