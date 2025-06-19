package com.ead.auth_service.controllers;

import com.ead.auth_service.configs.security.AuthenticationCurrentUserService;
import com.ead.auth_service.configs.security.UserDetailsImpl;
import com.ead.auth_service.dtos.UserRecordDto;
import com.ead.auth_service.models.UserModel;
import com.ead.auth_service.services.UserService;
import com.ead.auth_service.specifications.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController {

    Logger logger = LogManager.getLogger(UserController.class);

    final UserService userService;
    final AuthenticationCurrentUserService authenticationCurrentUserService;

    public UserController(UserService userService, AuthenticationCurrentUserService authenticationCurrentUserService) {
        this.userService = userService;
        this.authenticationCurrentUserService = authenticationCurrentUserService;
    }


    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(SpecificationTemplate.UserSpec spec,
                                                       Pageable pageable,
                                                       Authentication authentication) {
        UserDetails userDetails = (UserDetailsImpl) authentication.getPrincipal();
        logger.info("Authentication: {}", userDetails.getUsername());

        Page<UserModel> userModelPage = userService.findAll(spec, pageable);
        if(!userModelPage.isEmpty()) {
            for(UserModel user: userModelPage.toList()) {
                user.add(linkTo(methodOn(UserController.class).getUserById(user.getUserId())).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable(value = "userId") UUID userId){
        UUID currentUserId = authenticationCurrentUserService.getCurrentUser().getUserId();
        if(currentUserId.equals(userId)) {
            return ResponseEntity.status(HttpStatus.OK).body(userService.findById(userId).get());
        } else {
            throw new AccessDeniedException("Forbidden");
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "userId") UUID userId,
                                            @RequestBody @Validated(UserRecordDto.UserView.UserPut.class)
                                            @JsonView(UserRecordDto.UserView.UserPut.class)
                                            UserRecordDto userRecordDto) {
        logger.debug("PUT updateUser userRecordDto {}", userRecordDto);
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userRecordDto, userService.findById(userId).get()));
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable(value = "userId") UUID userId,
                                             @RequestBody  @Validated(UserRecordDto.UserView.PasswordPut.class)
                                             @JsonView(UserRecordDto.UserView.PasswordPut.class)
                                             UserRecordDto userRecordDto) {
        logger.debug("PUT updatePassword userId {}", userId);
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(!userModelOptional.get().getPassword().equals(userRecordDto.oldPassword())) {
            logger.warn("Password is incorrect! userId {}", userId);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Old password is wrong");
        }
        userService.updatePassword(userRecordDto, userModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Password updated with success");
    }

    @PutMapping("/{userId}/image")
    public ResponseEntity<Object> updateImage(@PathVariable(value = "userId") UUID userId,
                                                 @RequestBody  @Validated(UserRecordDto.UserView.ImagePut.class)
                                                 @JsonView(UserRecordDto.UserView.ImagePut.class)
                                                 UserRecordDto userRecordDto) {
        logger.debug("PUT updateImage userRecordDto {}", userRecordDto);
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateImage(userRecordDto, userService.findById(userId).get()));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId){
        logger.debug("DELETE deleteUser userId received {} ", userId);
        userService.delete(userService.findById(userId).get());
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully.");
    }

}
