package com.ead.auth_service.services;

import com.ead.auth_service.models.UserModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<UserModel> findAll();

    Optional<UserModel> findById(UUID userId);

    void deleteUserById(UserModel userModel);
}
