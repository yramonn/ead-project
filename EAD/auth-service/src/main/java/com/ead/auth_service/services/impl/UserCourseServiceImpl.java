package com.ead.auth_service.services.impl;

import com.ead.auth_service.models.UserCourseModel;
import com.ead.auth_service.models.UserModel;
import com.ead.auth_service.repositories.UserCourseRepository;
import com.ead.auth_service.services.UserCourseService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserCourseServiceImpl implements UserCourseService {

    final UserCourseRepository userCourseRepository;

    public UserCourseServiceImpl(UserCourseRepository userCourseRepository) {
        this.userCourseRepository = userCourseRepository;
    }

    @Override
    public boolean existsByUserAndCourseId(UserModel userModel, UUID courseId) {
       return userCourseRepository.existsByUserAndCourseId(userModel, courseId);
    }

    @Override
    public UserCourseModel save(UserCourseModel userCourseModel) {
        return userCourseRepository.save(userCourseModel);
    }

    @Override
    public boolean existsByCourseId(UUID courseId) {
       return userCourseRepository.existsByCourseId(courseId);
    }

    @Transactional
    @Override
    public void deleteAllByCourseId(UUID courseId) {
        userCourseRepository.deleteAllByCourseId(courseId);
    }
}
