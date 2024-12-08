package com.ead.courseservice.services;

import com.ead.courseservice.dtos.ModuleRecordDto;
import com.ead.courseservice.models.CourseModel;
import com.ead.courseservice.models.ModuleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleService {

    void delete(ModuleModel module);

    ModuleModel save(ModuleRecordDto moduleRecordDto, CourseModel courseModel);

    List<ModuleModel> findAllModulesIntoCourse(UUID courseId);

    Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID moduleId);

    ModuleModel update(ModuleRecordDto moduleRecordDto, ModuleModel moduleModel);

    Optional<ModuleModel> findById(UUID moduleId);

    Page<ModuleModel> findAllModulesIntoCourse(Specification<ModuleModel> spec, Pageable pageable);

}
