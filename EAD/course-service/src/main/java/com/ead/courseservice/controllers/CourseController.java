package com.ead.courseservice.controllers;

import com.ead.courseservice.dtos.CourseRecordDto;
import com.ead.courseservice.models.CourseModel;
import com.ead.courseservice.services.CourseService;
import com.ead.courseservice.specifications.SpecificationTemplate;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/courses")
public class CourseController {

    Logger logger = LogManager.getLogger(CourseController.class);
    final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<Object> saveCourse(@RequestBody @Valid CourseRecordDto courseRecordDto) {
        logger.info("POST saveCourse CourseRecordDto {}", courseRecordDto);
        if(courseService.existsByName(courseRecordDto.name())) {
            logger.warn("Course with name {} already exists", courseRecordDto.name());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Course already exists");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(courseRecordDto));
    }

    @GetMapping
    public ResponseEntity<Page<CourseModel>> getAllCourses(SpecificationTemplate.CourseSpec spec, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll(spec, pageable));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Object> getCourseById(@PathVariable(value = "courseId")UUID courseId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(courseService.findById(courseId).get());
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Object> deleteCourseById(@PathVariable(value = "courseId")UUID courseId) {
        logger.info("DELETE deleteCourseById courseId {}", courseId);
        courseService.delete(courseService.findById(courseId).get());
        return ResponseEntity.status(HttpStatus.OK).body("Deleted course with id " + courseId);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Object> updateCourse(@PathVariable(value = "courseId")UUID courseId,
                                                @RequestBody @Valid CourseRecordDto courseRecordDto) {
        logger.info("PUT updateCourse CourseRecordDto {}", courseRecordDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(courseService.update(courseRecordDto, courseService.findById(courseId).get()));
    }
}
