package com.ead.auth_service.services;

import com.ead.auth_service.models.UserCourseModel;
import com.ead.auth_service.models.UserModel;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface UserCourseService {

    boolean existsByUserAndCourseId(UserModel userModel,UUID uuid);
    UserCourseModel save(UserCourseModel userCourseModel);
    boolean existsByCourseId(UUID courseId);
    void deleteAllByCourseId(UUID courseId);
}
