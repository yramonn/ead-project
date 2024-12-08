package com.ead.courseservice.services.impl;

import com.ead.courseservice.dtos.ModuleRecordDto;
import com.ead.courseservice.exceptions.NotFoundException;
import com.ead.courseservice.models.CourseModel;
import com.ead.courseservice.models.LessonModel;
import com.ead.courseservice.models.ModuleModel;
import com.ead.courseservice.repositories.LessonRepository;
import com.ead.courseservice.repositories.ModuleRepository;
import com.ead.courseservice.services.ModuleService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ModuleServiceImpl implements ModuleService {

    final ModuleRepository moduleRepository;
    final LessonRepository lessonRepository;

    public ModuleServiceImpl(ModuleRepository moduleRepository, LessonRepository lessonRepository) {
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
    }

    @Transactional
    @Override
    public void delete(ModuleModel moduleModel) {
        List<LessonModel> lessonModelList = lessonRepository.findAllLessonsIntoModule(moduleModel.getModuleId());
        if(!lessonModelList.isEmpty()){
            lessonRepository.deleteAll(lessonModelList);
        }
        moduleRepository.delete(moduleModel);
    }

    @Override
    public ModuleModel save(ModuleRecordDto moduleRecordDto, CourseModel courseModel) {
       var moduleModel = new ModuleModel();
       BeanUtils.copyProperties(moduleRecordDto, moduleModel);
       moduleModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
       moduleModel.setCourse(courseModel);
       return moduleRepository.save(moduleModel);
    }

    @Override
    public List<ModuleModel> findAllModulesIntoCourse(UUID courseId) {
        return moduleRepository.findAllModulesIntoCourse(courseId);
    }

    @Override
    public Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID moduleId) {
        Optional<ModuleModel> moduleModelOptional = moduleRepository.findModuleIntoCourse(courseId, moduleId);
        if(moduleModelOptional.isEmpty()) {
            throw new NotFoundException("Error: Module not found for this course");
        }
        return moduleModelOptional;
    }

    @Override
    public ModuleModel update(ModuleRecordDto moduleRecordDto, ModuleModel moduleModel) {
        BeanUtils.copyProperties(moduleRecordDto, moduleModel);
        return moduleRepository.save(moduleModel);
    }

    @Override
    public Optional<ModuleModel> findById(UUID moduleId) {
        Optional<ModuleModel> moduleModelOptional = moduleRepository.findById(moduleId);
        if(moduleModelOptional.isEmpty()) {
            throw new NotFoundException("Error: Module not found for this course");
        }
        return moduleModelOptional;
    }

    @Override
    public Page<ModuleModel> findAllModulesIntoCourse(Specification<ModuleModel> spec, Pageable pageable) {
        return moduleRepository.findAll(spec, pageable);
    }
}
