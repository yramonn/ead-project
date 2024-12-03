package com.ead.courseservice.services;

import com.ead.courseservice.dtos.ModuleRecordDto;
import com.ead.courseservice.models.CourseModel;
import com.ead.courseservice.models.ModuleModel;
import jakarta.validation.Valid;

import java.util.UUID;

public interface ModuleService {

    void delete(ModuleModel module);

    ModuleModel save(@Valid ModuleRecordDto moduleRecordDto, CourseModel courseModel);
}
