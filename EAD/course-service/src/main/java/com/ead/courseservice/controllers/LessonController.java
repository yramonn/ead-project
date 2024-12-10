package com.ead.courseservice.controllers;

import com.ead.courseservice.dtos.LessonRecordDto;
import com.ead.courseservice.models.LessonModel;
import com.ead.courseservice.services.LessonService;
import com.ead.courseservice.services.ModuleService;
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
public class LessonController {

    Logger logger = LogManager.getLogger(LessonController.class);
    final LessonService lessonService;
    final ModuleService moduleService;

    public LessonController(LessonService lessonService, ModuleService moduleService) {
        this.lessonService = lessonService;
        this.moduleService = moduleService;
    }

    @PostMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<Object> saveLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                             @RequestBody @Valid LessonRecordDto lessonRecordDto) {
        logger.info("POST saveLesson lessonRecordDto {}", lessonRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(lessonService.save(lessonRecordDto, moduleService.findById(moduleId).get()));
    }

    @GetMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<Page<LessonModel>> getAllLessons(@PathVariable(value = "moduleId") UUID moduleId,
                                                           SpecificationTemplate.LessonSpec spec,
                                                           Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(lessonService.findAllLessonsIntoModule(SpecificationTemplate.lessonModuleId(moduleId).and(spec),pageable));
    }

    @GetMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> getLessonById(@PathVariable(value = "moduleId")UUID moduleId,
                                                @PathVariable(value = "lessonId")UUID lessonId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(lessonService.findLessonIntoModule(moduleId, lessonId).get());
    }

    @DeleteMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> deleteLessonById(@PathVariable(value = "moduleId")UUID moduleId,
                                                   @PathVariable(value = "lessonId")UUID lessonId) {
        logger.info("DELETE deleteLessonById moduleId {} lessonId {}", moduleId, lessonId);
        lessonService.delete(lessonService.findLessonIntoModule(moduleId, lessonId).get());
        String message = String.format("Lesson: %s of module: %s deleted with success", lessonId, moduleId);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PutMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> updateLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                               @PathVariable(value = "lessonId") UUID lessonId,
                                               @RequestBody @Valid LessonRecordDto lessonRecordDto) {
        logger.info("PUT updateLesson lessonRecordDto {}", lessonRecordDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(lessonService.update(lessonRecordDto, lessonService.findLessonIntoModule(moduleId, lessonId).get()));
    }
}
