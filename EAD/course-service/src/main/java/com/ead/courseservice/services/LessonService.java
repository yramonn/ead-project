package com.ead.courseservice.services;

import com.ead.courseservice.dtos.LessonRecordDto;
import com.ead.courseservice.models.LessonModel;
import com.ead.courseservice.models.ModuleModel;
import jakarta.validation.Valid;

public interface LessonService {
    LessonModel save(LessonRecordDto lessonRecordDto, ModuleModel moduleModel);
}
