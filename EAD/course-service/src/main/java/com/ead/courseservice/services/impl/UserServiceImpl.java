package com.ead.courseservice.services.impl;

import com.ead.courseservice.models.UserModel;
import com.ead.courseservice.repositories.UserRepository;
import com.ead.courseservice.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {
       return userRepository.findAll(spec, pageable);
    }
}
