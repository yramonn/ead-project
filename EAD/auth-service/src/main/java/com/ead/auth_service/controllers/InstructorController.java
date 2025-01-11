package com.ead.auth_service.controllers;

import com.ead.auth_service.dtos.InstructorRecordDto;
import com.ead.auth_service.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/instructors")
public class InstructorController {

    final UserService userService;

    public InstructorController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/subscription")
    public ResponseEntity<Object> saveSubscriptionInstructor(@RequestBody @Valid InstructorRecordDto instructorRecordDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.registerInstructor(userService.findById(instructorRecordDto.userId()).get()));
    }
}
