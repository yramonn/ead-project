package com.ead.courseservice.services;

import com.ead.courseservice.dtos.LessonRecordDto;
import com.ead.courseservice.models.LessonModel;
import com.ead.courseservice.models.ModuleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonService {
    LessonModel save(LessonRecordDto lessonRecordDto, ModuleModel moduleModel);

    List<LessonModel> findAllLessonsIntoModule(UUID moduleId);

    Optional<LessonModel> findLessonIntoModule(UUID lessonId, UUID moduleId);

    void delete(LessonModel lessonModel);

    LessonModel update(LessonRecordDto lessonRecordDto, LessonModel lessonModel);

    Page<LessonModel> findAllLessonsIntoModule(Specification<LessonModel> spec, Pageable pageable);
}
