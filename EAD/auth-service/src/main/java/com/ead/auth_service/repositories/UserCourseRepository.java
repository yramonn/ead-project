package com.ead.auth_service.repositories;

import com.ead.auth_service.models.UserCourseModel;
import com.ead.auth_service.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserCourseRepository extends JpaRepository<UserCourseModel, UUID> {

    boolean existsByUserAndCourseId(UserModel userModel, UUID courseId);
}
