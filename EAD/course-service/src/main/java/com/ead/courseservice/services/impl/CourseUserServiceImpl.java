package com.ead.courseservice.services.impl;

import com.ead.courseservice.models.CourseModel;
import com.ead.courseservice.models.CourseUserModel;
import com.ead.courseservice.repositories.CourseUserRepository;
import com.ead.courseservice.services.CourseUserService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CourseUserServiceImpl implements CourseUserService {

    final CourseUserRepository courseUserRepository;

    public CourseUserServiceImpl(CourseUserRepository courseUserRepository) {
        this.courseUserRepository = courseUserRepository;
    }

    @Override
    public boolean existsByCourseAndUserId(CourseModel courseModel, UUID userId) {
        return courseUserRepository.existsByCourseAndUserId(courseModel, userId);
    }

    @Override
    public CourseUserModel saveAndSendSubscriptionUserInCourse(CourseUserModel courseUserModel) {
        courseUserModel = courseUserRepository.save(courseUserModel);
        //send to auth user
        return courseUserModel;
    }
}
