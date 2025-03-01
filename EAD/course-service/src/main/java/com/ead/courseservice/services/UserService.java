package com.ead.courseservice.services;

import com.ead.courseservice.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface UserService {

    Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable);
    UserModel save(UserModel userModel);
}
