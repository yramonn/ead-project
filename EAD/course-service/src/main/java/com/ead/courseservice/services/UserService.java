package com.ead.courseservice.services;

import com.ead.courseservice.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable);
    UserModel save(UserModel userModel);
    void delete(UUID userId);

    Optional<UserModel> findById(UUID userId);
}
