package com.ead.paymentservice.services.impl;

import com.ead.paymentservice.exceptions.NotFoundException;
import com.ead.paymentservice.models.UserModel;
import com.ead.paymentservice.repositories.UserRepository;
import com.ead.paymentservice.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }

    @Override
    public void delete(UUID userId) {
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
