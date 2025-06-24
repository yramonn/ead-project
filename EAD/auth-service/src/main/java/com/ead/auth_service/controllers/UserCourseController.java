package com.ead.auth_service.controllers;

import com.ead.auth_service.clients.CourseClient;
import com.ead.auth_service.configs.security.AuthenticationCurrentUserService;
import com.ead.auth_service.configs.security.UserDetailsImpl;
import com.ead.auth_service.dtos.CourseRecordDto;
import com.ead.auth_service.enums.RoleType;
import com.ead.auth_service.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserCourseController {

    final CourseClient courseClient;
    final UserService userService;
    final AuthenticationCurrentUserService authenticationCurrentUserService;

    public UserCourseController(CourseClient courseClient, UserService userService, AuthenticationCurrentUserService authenticationCurrentUserService) {
        this.courseClient = courseClient;
        this.userService = userService;
        this.authenticationCurrentUserService = authenticationCurrentUserService;
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<Page<CourseRecordDto>> getAllCoursesByUser(@PageableDefault(sort = "courseId", direction = Sort.Direction.ASC)Pageable pageable,
                                                                     @PathVariable(value = "userId") UUID userId,
                                                                     @RequestHeader("Authorization") String token) {

        UserDetailsImpl userDetails = authenticationCurrentUserService.getCurrentUser();
        if (userDetails.getUserId().equals(userId) || userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            userService.findById(userId);
            return ResponseEntity.status(HttpStatus.OK).body(courseClient.getAllCoursesByUser(userId, pageable, token));
        } else {
            throw new AccessDeniedException("Forbidden");
        }
    }
}
