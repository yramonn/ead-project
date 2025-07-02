package com.ead.notificationservice.adapters.inbounds.controllers;

import com.ead.notificationservice.adapters.configs.security.AuthenticationCurrentUserService;
import com.ead.notificationservice.adapters.configs.security.UserDetailsImpl;
import com.ead.notificationservice.adapters.dtos.NotificationRecordDto;
import com.ead.notificationservice.core.domain.NotificationDomain;
import com.ead.notificationservice.core.domain.PageInfo;
import com.ead.notificationservice.core.ports.NotificationServicePort;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class UserNotificationController {

    final NotificationServicePort notificationServicePort;
    final AuthenticationCurrentUserService authenticationCurrentUserService;

    public UserNotificationController(NotificationServicePort notificationServicePort, AuthenticationCurrentUserService authenticationCurrentUserService) {
        this.notificationServicePort = notificationServicePort;
        this.authenticationCurrentUserService = authenticationCurrentUserService;
    }


    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/users/{userId}/notifications")
    public ResponseEntity<Page<NotificationDomain>> getAllNotificationsByUser(@PathVariable(value = "userId") UUID userId,
                                                                              Pageable pageable) {
        UserDetailsImpl userDetails = authenticationCurrentUserService.getCurrentUser();
        if(userDetails.getUserId().equals(userId) || userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            var pageInfo = new PageInfo();
            BeanUtils.copyProperties(pageable, pageInfo);
            List<NotificationDomain> notificationDomainList = notificationServicePort.findAllNotificationsByUser(userId, pageInfo);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new PageImpl<>(notificationDomainList, pageable, notificationDomainList.size()));
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
                    .body(notificationServicePort.updateNotification(notificationRecordDto.notificationStatus(), notificationServicePort.findByNotificationIdAndUserId(notificationId, userId).get()));
        } else {
            throw new AccessDeniedException("Forbidden");
        }
    }
}
