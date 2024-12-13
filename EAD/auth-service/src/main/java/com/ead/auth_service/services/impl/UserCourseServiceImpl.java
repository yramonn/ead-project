package com.ead.auth_service.services.impl;

import com.ead.auth_service.repositories.UserCourseRepository;
import com.ead.auth_service.services.UserCourseService;
import org.springframework.stereotype.Service;

@Service
public class UserCourseServiceImpl implements UserCourseService {

    final UserCourseRepository userCourseRepository;

    public UserCourseServiceImpl(UserCourseRepository userCourseRepository) {
        this.userCourseRepository = userCourseRepository;
    }
}
