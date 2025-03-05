package com.ead.courseservice.services.impl;

import com.ead.courseservice.exceptions.NotFoundException;
import com.ead.courseservice.models.UserModel;
import com.ead.courseservice.repositories.CourseRepository;
import com.ead.courseservice.repositories.UserRepository;
import com.ead.courseservice.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;
    final CourseRepository courseRepository;

    public UserServiceImpl(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {
       return userRepository.findAll(spec, pageable);
    }

    @Override
    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }

    @Override
    public void delete(UUID userId) {
        courseRepository.deleteCourseUserByUser(userId);
        userRepository.deleteById(userId);
    }

    @Override
    public Optional<UserModel> findById(UUID userId) {
        Optional<UserModel> userModelOptional = userRepository.findById(userId);
        if(userModelOptional.isEmpty()){
            throw new NotFoundException("Error: User not found");
        }
        return userModelOptional;
    }
}
