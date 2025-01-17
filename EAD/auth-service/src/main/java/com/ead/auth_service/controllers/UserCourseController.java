package com.ead.auth_service.controllers;

import com.ead.auth_service.clients.CourseClient;
import com.ead.auth_service.dtos.CourseRecordDto;
import com.ead.auth_service.dtos.UserCourseRecordDto;
import com.ead.auth_service.models.UserCourseModel;
import com.ead.auth_service.models.UserModel;
import com.ead.auth_service.services.UserCourseService;
import com.ead.auth_service.services.UserService;
import jakarta.validation.Valid;
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
public class UserCourseController {

    final CourseClient courseClient;
    final UserService userService;
    final UserCourseService userCourseService;

    public UserCourseController(CourseClient courseClient, UserService userService, UserCourseService userCourseService) {
        this.courseClient = courseClient;
        this.userService = userService;
        this.userCourseService = userCourseService;
    }

    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<Page<CourseRecordDto>> getAllCoursesByUser(@PageableDefault(sort = "courseId", direction = Sort.Direction.ASC)Pageable pageable,
                                                                     @PathVariable(value = "userId") UUID userId) {
        userService.findById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(courseClient.getAllCoursesByUser(userId, pageable));

    }

    @PostMapping("/users/{userId}/courses/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable(value = "userId") UUID userId,
                                                               @RequestBody @Valid UserCourseRecordDto userCourseRecordDto) {

        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(userCourseService.existsByUserAndCourseId(userModelOptional.get(), userCourseRecordDto.courseId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Subscription already exists!");
        }

        UserCourseModel userCourseModel =
                userCourseService.save(userModelOptional.get().convertToUserCourseModel(userCourseRecordDto.courseId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(userCourseModel);

    }

    @DeleteMapping("/users/courses/{courseId}")
    public ResponseEntity<Object> deleteUserCourseByCourse(@PathVariable(value = "courseId") UUID courseId) {
        if(!userCourseService.existsByCourseId(courseId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Course does not exist!");
        }
        userCourseService.deleteAllByCourseId(courseId);
        return ResponseEntity.status(HttpStatus.OK).body("User course deleted with success!");
    }
}
