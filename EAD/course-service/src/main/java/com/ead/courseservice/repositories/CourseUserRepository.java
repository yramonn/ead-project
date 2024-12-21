package com.ead.courseservice.repositories;

import com.ead.courseservice.models.CourseModel;
import com.ead.courseservice.models.CourseUserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseUserRepository extends JpaRepository<CourseUserModel, UUID> {

    boolean existsByCourseAndUserId(CourseModel course, UUID userId);
}
