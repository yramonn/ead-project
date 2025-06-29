package com.ead.notificationservice.adapters.outbounds.persistences;

import com.ead.notificationservice.adapters.outbounds.entities.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationJpaRepository extends JpaRepository<NotificationEntity, UUID> {
}
