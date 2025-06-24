package com.ead.courseservice.controllers;

import com.ead.courseservice.dtos.ModuleRecordDto;
import com.ead.courseservice.models.ModuleModel;
import com.ead.courseservice.services.CourseService;
import com.ead.courseservice.services.ModuleService;
import com.ead.courseservice.specifications.SpecificationTemplate;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class ModuleController {

    Logger logger = LogManager.getLogger(ModuleController.class);

    final ModuleService moduleService;
    final  CourseService courseService;

    public ModuleController(ModuleService moduleService, CourseService courseService) {
        this.moduleService = moduleService;
        this.courseService = courseService;
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<Object> saveModule(@PathVariable(value = "courseId") UUID courseId,
                                             @RequestBody @Valid ModuleRecordDto moduleRecordDto) {
        logger.info("POST saveModule moduleRecordDto {}", moduleRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(moduleService.save(moduleRecordDto, courseService.findById(courseId).get()));
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/courses/{courseId}/modules")
    public ResponseEntity<Page<ModuleModel>> getAllModules(@PathVariable(value = "courseId") UUID courseId,
                                                           SpecificationTemplate.ModuleSpec spec,
                                                           Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK)
                .body(moduleService.findAllModulesIntoCourse(SpecificationTemplate.moduleCourseId(courseId).and(spec), pageable));
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> getModuleById(@PathVariable(value = "courseId")UUID courseId,
                                                @PathVariable(value = "moduleId")UUID moduleId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(moduleService.findModuleIntoCourse(courseId, moduleId));
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> deleteModuleById(@PathVariable(value = "courseId")UUID courseId,
                                                   @PathVariable(value = "moduleId")UUID moduleId) {
        logger.info("DELETE deleteModuleById moduleId {}", moduleId);
        moduleService.delete(moduleService.findModuleIntoCourse(courseId, moduleId).get());
        String message = String.format("Module: %s of course: %s deleted with success", moduleId, courseId);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PutMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> updateModule(@PathVariable(value = "courseId")UUID courseId,
                                               @PathVariable(value = "moduleId")UUID moduleId,
                                               @RequestBody @Valid ModuleRecordDto moduleRecordDto) {
        logger.info("PUT updateModule moduleRecordDto {}", moduleRecordDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(moduleService.update(moduleRecordDto, moduleService.findModuleIntoCourse(courseId, moduleId).get()));
    }
}
