package com.ead.auth_service.controllers;

import com.ead.auth_service.configs.security.JwtProvider;
import com.ead.auth_service.dtos.JwtRecordDto;
import com.ead.auth_service.dtos.LoginRecordDto;
import com.ead.auth_service.dtos.UserRecordDto;
import com.ead.auth_service.services.UserService;
import com.ead.auth_service.validations.UserValidator;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
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
    final UserValidator userValidator;
    final AuthenticationManager authenticationManager;
    final JwtProvider jwtProvider;

    public AuthenticationController(UserService userService, UserValidator userValidator, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody  @Validated(UserRecordDto.UserView.RegistrationPost.class)
                                                   @JsonView(UserRecordDto.UserView.RegistrationPost.class) UserRecordDto userRecordDto,
                                                    Errors errors) {
        logger.debug("POST registerUser userRecordDto received {}", userRecordDto);
        userValidator.validate(userRecordDto, errors);
       if(errors.hasErrors()) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
       }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(userRecordDto));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtRecordDto> authenticateUser(@Valid @RequestBody LoginRecordDto loginRecordDto) {
        logger.debug("POST loginRecordDto received {}", loginRecordDto);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRecordDto.username(), loginRecordDto.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new JwtRecordDto(jwtProvider.generateJwt(authentication)));
    }
}
