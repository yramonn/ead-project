package com.ead.courseservice.services.impl;

import com.ead.courseservice.dtos.CourseRecordDto;
import com.ead.courseservice.dtos.NotificationRecordCommandDto;
import com.ead.courseservice.exceptions.NotFoundException;
import com.ead.courseservice.models.CourseModel;
import com.ead.courseservice.models.LessonModel;
import com.ead.courseservice.models.ModuleModel;
import com.ead.courseservice.models.UserModel;
import com.ead.courseservice.publishers.NotificationCommandPublisher;
import com.ead.courseservice.repositories.CourseRepository;
import com.ead.courseservice.repositories.LessonRepository;
import com.ead.courseservice.repositories.ModuleRepository;
import com.ead.courseservice.services.CourseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class CourseServiceImpl implements CourseService {

    Logger logger = LogManager.getLogger(CourseServiceImpl.class);

    final CourseRepository courseRepository;
    final ModuleRepository moduleRepository;
    final LessonRepository lessonRepository;
    final NotificationCommandPublisher notificationCommandPublisher;

    public CourseServiceImpl(CourseRepository courseRepository, ModuleRepository moduleRepository, LessonRepository lessonRepository, NotificationCommandPublisher notificationCommandPublisher) {
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
        this.notificationCommandPublisher = notificationCommandPublisher;
    }

    @Transactional
    @Override
    public void delete(CourseModel courseModel) {
        List<ModuleModel> moduleModelList = moduleRepository.findAllModulesIntoCourse(courseModel.getCourseId());
        if(!moduleModelList.isEmpty()){
            for(ModuleModel module : moduleModelList) {
                List<LessonModel> lessonModelList = lessonRepository.findAllLessonsIntoModule(module.getModuleId());
                if(!lessonModelList.isEmpty()){
                    lessonRepository.deleteAll(lessonModelList);
                }
            }
            moduleRepository.deleteAll(moduleModelList);
        }
        courseRepository.deleteCourseUserByCourse(courseModel.getCourseId());
        courseRepository.delete(courseModel);
    }

    @Override
    public CourseModel save(CourseRecordDto courseRecordDto) {
        var courseModel = new CourseModel();
        BeanUtils.copyProperties(courseRecordDto, courseModel);
        courseModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        courseModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return courseRepository.save(courseModel);
    }

    @Override
    public boolean existsByName(String name) {
        return courseRepository.existsByName(name);
    }

    @Override
    public Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable) {
        return courseRepository.findAll(spec, pageable);
    }

    @Override
    public Optional<CourseModel> findById(UUID courseId) {
        Optional<CourseModel> courseModelOptional = courseRepository.findById(courseId);
        if(courseModelOptional.isEmpty()){
            throw new NotFoundException("Error: Course not found");
        }
        return courseModelOptional;
    }

    @Override
    public CourseModel update(CourseRecordDto courseRecordDto, CourseModel courseModel) {
        BeanUtils.copyProperties(courseRecordDto, courseModel);
        courseModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return courseRepository.save(courseModel);
    }

    @Override
    public boolean existsByCourseAndUser(UUID courseId, UUID userId) {
        return courseRepository.existsByCourseAndUser(courseId, userId);
    }
    @Transactional
    @Override
    public void saveSubscriptionUserInCourse(CourseModel courseModel, UserModel userModel) {
        courseRepository.saveCourseUser(courseModel.getCourseId(), userModel.getUserId());

        try {
            var notificationRecordCommandDto = new NotificationRecordCommandDto(
                    "Bem-Vindo(a) ao Curso: " + courseModel.getName(),
                    userModel.getFullName() + "a sua inscrição foi realizada com sucesso!",
                    userModel.getUserId());
            notificationCommandPublisher.publishNotificationCommand(notificationRecordCommandDto);
        } catch(Exception e) {
            logger.error("Error sending notification!");
        }
    }
}
