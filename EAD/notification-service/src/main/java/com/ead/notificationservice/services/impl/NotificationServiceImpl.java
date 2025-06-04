package com.ead.notificationservice.services.impl;

import com.ead.notificationservice.dtos.NotificationRecordCommandDto;
import com.ead.notificationservice.dtos.NotificationRecordDto;
import com.ead.notificationservice.enums.NotificationStatus;
import com.ead.notificationservice.exceptions.NotFoundException;
import com.ead.notificationservice.models.NotificationModel;
import com.ead.notificationservice.repositories.NotificationRepository;
import com.ead.notificationservice.services.NotificationService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationServiceImpl implements NotificationService {

    final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public NotificationModel saveNotification(NotificationRecordCommandDto notificationRecordCommandDto) {
        var notificationModel = new NotificationModel();
        BeanUtils.copyProperties(notificationRecordCommandDto, notificationModel);
        notificationModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        notificationModel.setNotificationStatus(NotificationStatus.CREATED);

        return notificationRepository.save(notificationModel);
    }

    @Override
    public Page<NotificationModel> findAllNotificationsByUser(UUID userId, Pageable pageable) {
        return notificationRepository.findAllByUserIdAndNotificationStatus(userId, NotificationStatus.CREATED, pageable);
    }

    @Override
    public Optional<NotificationModel> findByNotificationIdAndUserId(UUID notificationId, UUID userId) {
        Optional<NotificationModel> notificationModelOptional =
                notificationRepository.findByNotificationIdAndUserId(notificationId, userId);

        if(notificationModelOptional.isEmpty()) {
            throw new NotFoundException("Error: Notification not found");
        }
        return notificationModelOptional;
    }

    @Override
    public NotificationModel updateNotification(NotificationRecordDto notificationRecordDto, NotificationModel notificationModel) {
        notificationModel.setNotificationStatus(notificationRecordDto.notificationStatus());
        return notificationRepository.save(notificationModel);
    }
}
