package com.ead.auth_service.controllers;

import com.ead.auth_service.dtos.UserRecordDto;
import com.ead.auth_service.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    Logger logger = LogManager.getLogger(AuthenticationController.class);

    final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody  @Validated(UserRecordDto.UserView.RegistrationPost.class)
                                                   @JsonView(UserRecordDto.UserView.RegistrationPost.class) UserRecordDto userRecordDto) {
        logger.debug("POST registerUser userRecordDto received {}", userRecordDto);
        if(userService.existsByUsername(userRecordDto.username())) {
            logger.warn("Username {} already exists", userRecordDto.username());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username already exists");
        }
        if(userService.existsByEmail(userRecordDto.email())) {
            logger.warn("Email {} already exists", userRecordDto.email());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email already exists");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(userRecordDto));

    }
}
