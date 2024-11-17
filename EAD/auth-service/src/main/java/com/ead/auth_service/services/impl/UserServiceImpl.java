package com.ead.auth_service.services.impl;

import com.ead.auth_service.exceptions.NotFoundException;
import com.ead.auth_service.models.UserModel;
import com.ead.auth_service.repositories.UserRepository;
import com.ead.auth_service.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserModel> findById(UUID userId) {
        Optional<UserModel> userModelOptional = userRepository.findById(userId);
        if(userModelOptional.isEmpty()) {
            throw new NotFoundException("Error: User not found");
        }
        return userModelOptional;
    }

    @Override
    public void deleteUserById(UserModel userModel) {
         userRepository.delete(userModel);
    }
}
