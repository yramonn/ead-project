package com.ead.courseservice.repositories;

import com.ead.courseservice.models.CourseUserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseUserModelRepository extends JpaRepository<CourseUserModel, UUID> {
}
