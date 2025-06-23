package com.ead.notificationservice.controllers;

import com.ead.notificationservice.configs.security.AuthenticationCurrentUserService;
import com.ead.notificationservice.configs.security.UserDetailsImpl;
import com.ead.notificationservice.dtos.NotificationRecordDto;
import com.ead.notificationservice.models.NotificationModel;
import com.ead.notificationservice.services.NotificationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserNotificationController {

    final NotificationService notificationService;
    final AuthenticationCurrentUserService authenticationCurrentUserService;

    public UserNotificationController(NotificationService notificationService, AuthenticationCurrentUserService authenticationCurrentUserService) {
        this.notificationService = notificationService;
        this.authenticationCurrentUserService = authenticationCurrentUserService;
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/users/{userId}/notifications")
    public ResponseEntity<Page<NotificationModel>> getAllNotificationsByUser(@PathVariable(value = "userId") UUID userId,
                                                                             Pageable pageable) {
        UserDetailsImpl userDetails = authenticationCurrentUserService.getCurrentUser();
        if(userDetails.getUserId().equals(userId) || userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(notificationService.findAllNotificationsByUser(userId, pageable));
        } else {
            throw new AccessDeniedException("Forbidden");
        }
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("/users/{userId}/notifications/{notificationId}")
    public ResponseEntity<Object> updateNotification(@PathVariable(value = "userId") UUID userId,
                                                     @PathVariable(value = "notificationId") UUID notificationId,
                                                     @RequestBody @Valid NotificationRecordDto notificationRecordDto) {
        UserDetailsImpl userDetails = authenticationCurrentUserService.getCurrentUser();
        if(userDetails.getUserId().equals(userId) || userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(notificationService.updateNotification(notificationRecordDto, notificationService.findByNotificationIdAndUserId(notificationId, userId).get()));
        } else {
            throw new AccessDeniedException("Forbidden");
        }
    }
}
