package com.ead.notificationservice.services.impl;

import com.ead.notificationservice.dtos.NotificationRecordCommandDto;
import com.ead.notificationservice.enums.NotificationStatus;
import com.ead.notificationservice.models.NotificationModel;
import com.ead.notificationservice.repositories.NotificationRepository;
import com.ead.notificationservice.services.NotificationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

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
}
