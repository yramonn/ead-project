package com.ead.courseservice.services.impl;

import com.ead.courseservice.clients.AuthUserClient;
import com.ead.courseservice.models.CourseModel;
import com.ead.courseservice.models.CourseUserModel;
import com.ead.courseservice.repositories.CourseUserRepository;
import com.ead.courseservice.services.CourseUserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CourseUserServiceImpl implements CourseUserService {

    final CourseUserRepository courseUserRepository;
    private final AuthUserClient authUserClient;

    public CourseUserServiceImpl(CourseUserRepository courseUserRepository, AuthUserClient authUserClient) {
        this.courseUserRepository = courseUserRepository;
        this.authUserClient = authUserClient;
    }

    @Override
    public boolean existsByCourseAndUserId(CourseModel courseModel, UUID userId) {
        return courseUserRepository.existsByCourseAndUserId(courseModel, userId);
    }

    @Transactional
    @Override
    public CourseUserModel saveAndSendSubscriptionUserInCourse(CourseUserModel courseUserModel) {
        courseUserModel = courseUserRepository.save(courseUserModel);
        authUserClient.postSubscriptionUserInCourse(courseUserModel.getUserId(),courseUserModel.getCourse().getCourseId());
        return courseUserModel;
    }
}
