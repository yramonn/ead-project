package com.ead.courseservice.services;

import com.ead.courseservice.models.CourseModel;
import com.ead.courseservice.models.CourseUserModel;

import java.util.UUID;

public interface CourseUserService {

    boolean existsByCourseAndUserId(CourseModel courseModel, UUID userId);
    CourseUserModel saveAndSendSubscriptionUserInCourse(CourseUserModel courseUserModel);
}
