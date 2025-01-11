package com.ead.courseservice.validations;

import com.ead.courseservice.clients.AuthUserClient;
import com.ead.courseservice.dtos.CourseRecordDto;
import com.ead.courseservice.dtos.UserRecordDto;
import com.ead.courseservice.enums.Usertype;
import com.ead.courseservice.services.CourseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.UUID;

@Component
public class CourseValidator implements Validator {

    Logger logger = LogManager.getLogger(CourseValidator.class);

    private final Validator validator;
    final CourseService courseService;
    final AuthUserClient authUserClient;

    public CourseValidator(@Qualifier("defaultValidator") Validator validator, CourseService courseService, AuthUserClient authUserClient) {
        this.validator = validator;
        this.courseService = courseService;
        this.authUserClient = authUserClient;
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
        ResponseEntity<UserRecordDto> responseUserInstructor = authUserClient.getOneUserById(userInstructor);
        if(responseUserInstructor.getBody().usertype().equals(Usertype.STUDENT) ||
                responseUserInstructor.getBody().usertype().equals(Usertype.USER)) {
                errors.rejectValue("userInstructor", "UserInstructorError", "User must be INSTRUCTOR or ADMIN.");
                logger.error("Error validation userInstructor: {}", userInstructor);
        }
    }
}
