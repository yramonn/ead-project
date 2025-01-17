package com.ead.courseservice.controllers;


import com.ead.courseservice.clients.AuthUserClient;
import com.ead.courseservice.dtos.SubscriptionRecordDto;
import com.ead.courseservice.dtos.UserRecordDto;
import com.ead.courseservice.enums.UserStatus;
import com.ead.courseservice.models.CourseModel;
import com.ead.courseservice.models.CourseUserModel;
import com.ead.courseservice.services.CourseService;
import com.ead.courseservice.services.CourseUserService;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
public class CourseUserController {

    Logger logger = LogManager.getLogger(CourseUserController.class);

    final AuthUserClient authUserClient;
    final CourseService courseService;
    final CourseUserService courseUserService;

    public CourseUserController(AuthUserClient authUserClient, CourseService courseService, CourseUserService courseUserService) {
        this.authUserClient = authUserClient;
        this.courseService = courseService;
        this.courseUserService = courseUserService;
    }

    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Page<UserRecordDto>> getAllUsersByCourse(@PageableDefault(sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
                                                                   @PathVariable(value = "courseId") UUID courseId) {
        return ResponseEntity.status(HttpStatus.OK).body(authUserClient.getAllUsersByCourse(courseId, pageable));
    }

    @PostMapping("/courses/{courseId}/users/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable(value = "courseId") UUID courseId,
                                                               @RequestBody @Valid SubscriptionRecordDto subscriptionRecordDto) {
        Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
        if(courseUserService.existsByCourseAndUserId(courseModelOptional.get(), subscriptionRecordDto.userId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Subscription already exists");
        }
        ResponseEntity<UserRecordDto> responseUser = authUserClient.getOneUserById(subscriptionRecordDto.userId());
        if(responseUser.getBody().userStatus().equals(UserStatus.BLOCKED)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: User is blocked");
        }

        CourseUserModel courseUserModel =
                courseUserService.saveAndSendSubscriptionUserInCourse(courseModelOptional.get().convertToCourseUserModel(subscriptionRecordDto.userId()));
        logger.info("Subscription User {} save with Success", subscriptionRecordDto.userId());
        return ResponseEntity.status(HttpStatus.CREATED).body(courseUserModel);
    }


}
