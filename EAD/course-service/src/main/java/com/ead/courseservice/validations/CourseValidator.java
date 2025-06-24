package com.ead.courseservice.validations;

import com.ead.courseservice.configs.security.AuthenticationCurrentUserService;
import com.ead.courseservice.configs.security.UserDetailsImpl;
import com.ead.courseservice.dtos.CourseRecordDto;
import com.ead.courseservice.enums.UserType;
import com.ead.courseservice.models.UserModel;
import com.ead.courseservice.services.CourseService;
import com.ead.courseservice.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;
import java.util.UUID;

@Component
public class CourseValidator implements Validator {

    Logger logger = LogManager.getLogger(CourseValidator.class);

    private final Validator validator;
    final CourseService courseService;
    final UserService userService;
    final AuthenticationCurrentUserService authenticationCurrentUserService;

    public CourseValidator(@Qualifier("defaultValidator") Validator validator, CourseService courseService, UserService userService, AuthenticationCurrentUserService authenticationCurrentUserService, AuthenticationCurrentUserService authenticationCurrentUserService1) {
        this.validator = validator;
        this.courseService = courseService;
        this.userService = userService;
        this.authenticationCurrentUserService = authenticationCurrentUserService1;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        CourseRecordDto courseRecordDto = (CourseRecordDto) o;
        validator.validate(courseRecordDto, errors);
        if(!errors.hasErrors()) {
            validateCourseName(courseRecordDto, errors);
            validateUserInstructor(courseRecordDto.userInstructor(), errors);
        }
    }

    private void validateCourseName(CourseRecordDto courseRecordDto, Errors errors) {
        if(courseService.existsByName(courseRecordDto.name())) {
            errors.rejectValue("name", "courseNameConflict", "Course name already exists.");
            logger.error("Error validation courseName: {}", courseRecordDto.name());
        }
    }

    private void validateUserInstructor(UUID userInstructor, Errors errors) {
        UserDetailsImpl userDetails = authenticationCurrentUserService.getCurrentUser();
        if(userDetails.getUserId().equals(userInstructor) || userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            Optional<UserModel> userModelOptional = userService.findById(userInstructor);
            if (userModelOptional.get().getUsertype().equals(UserType.STUDENT.toString()) ||
                    userModelOptional.get().getUsertype().equals(UserType.USER.toString())) {
                errors.rejectValue("userInstructor", "UserInstructorError", "User must be INSTRUCTOR or ADMIN.");
                logger.error("Error validation userInstructor: {}", userInstructor);
            }

        } else {
            throw new AccessDeniedException("Forbidden");
        }
    }

}
