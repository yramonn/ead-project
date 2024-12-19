package com.ead.courseservice.controllers;


import com.ead.courseservice.clients.AuthUserClient;
import com.ead.courseservice.dtos.UserRecordDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class CourseUserController {

    final AuthUserClient authUserClient;

    public CourseUserController(AuthUserClient authUserClient) {
        this.authUserClient = authUserClient;
    }

    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Page<UserRecordDto>> getAllUsersByCourse(@PageableDefault(sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
                                                                   @PathVariable(value = "courseId") UUID courseId) {

        return ResponseEntity.status(HttpStatus.OK).body(authUserClient.getAllUsersByCourse(courseId, pageable));

    }


}
