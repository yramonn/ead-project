package com.ead.courseservice.controllers;

import com.ead.courseservice.dtos.CourseRecordDto;
import com.ead.courseservice.models.CourseModel;
import com.ead.courseservice.services.CourseService;
import com.ead.courseservice.specifications.SpecificationTemplate;
import com.ead.courseservice.validations.CourseValidator;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/courses")
public class CourseController {

    Logger logger = LogManager.getLogger(CourseController.class);
    final CourseService courseService;
    final CourseValidator courseValidator;

    public CourseController(CourseService courseService, CourseValidator courseValidator) {
        this.courseService = courseService;
        this.courseValidator = courseValidator;
    }

    @PostMapping
    public ResponseEntity<Object> saveCourse(@RequestBody  CourseRecordDto courseRecordDto,
                                             Errors errors) {
        logger.debug("POST saveCourse CourseRecordDto {}", courseRecordDto);
        courseValidator.validate(courseRecordDto, errors);
        if (errors.hasErrors()) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(courseRecordDto));
    }

    @GetMapping
    public ResponseEntity<Page<CourseModel>> getAllCourses(SpecificationTemplate.CourseSpec spec,
                                                           Pageable pageable,
                                                           @RequestParam(required = false)UUID userId) {
        Page<CourseModel> courseModelPage = (userId != null)
                ? courseService.findAll(SpecificationTemplate.courseUserId(userId).and(spec), pageable)
                : courseService.findAll(spec, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(courseModelPage);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Object> getCourseById(@PathVariable(value = "courseId")UUID courseId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(courseService.findById(courseId).get());
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Object> deleteCourseById(@PathVariable(value = "courseId")UUID courseId) {
        logger.debug("DELETE deleteCourseById courseId {}", courseId);
        courseService.delete(courseService.findById(courseId).get());
        return ResponseEntity.status(HttpStatus.OK).body("Deleted course with id " + courseId);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Object> updateCourse(@PathVariable(value = "courseId")UUID courseId,
                                                @RequestBody @Valid CourseRecordDto courseRecordDto) {
        logger.debug("PUT updateCourse CourseRecordDto {}", courseRecordDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(courseService.update(courseRecordDto, courseService.findById(courseId).get()));
    }
}
