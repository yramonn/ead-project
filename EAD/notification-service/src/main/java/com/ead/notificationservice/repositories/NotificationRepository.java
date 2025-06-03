package com.ead.notificationservice.repositories;

import com.ead.notificationservice.models.NotificationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotificationRepository  extends JpaRepository<NotificationModel, UUID> {
}
