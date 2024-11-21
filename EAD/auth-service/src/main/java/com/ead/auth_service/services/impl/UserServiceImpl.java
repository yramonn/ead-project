package com.ead.auth_service.services.impl;

import com.ead.auth_service.dtos.UserRecordDto;
import com.ead.auth_service.enums.UserStatus;
import com.ead.auth_service.enums.Usertype;
import com.ead.auth_service.exceptions.NotFoundException;
import com.ead.auth_service.models.UserModel;
import com.ead.auth_service.repositories.UserRepository;
import com.ead.auth_service.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
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

    @Override
    public UserModel registerUser(UserRecordDto userRecordDto) {
        var userModel = new UserModel();
        BeanUtils.copyProperties(userRecordDto, userModel);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUsertype(Usertype.USER);
        userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return userRepository.save(userModel);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserModel updateUser(UserRecordDto userRecordDto, UserModel userModel) {
       userModel.setFullName(userRecordDto.fullName());
       userModel.setPhoneNumber(userRecordDto.phoneNumber());
       userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
       return userRepository.save(userModel);
    }

    @Override
    public UserModel updatePassword(UserRecordDto userRecordDto, UserModel userModel) {
       userModel.setPassword(userRecordDto.password());
       userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
       return userRepository.save(userModel);
    }

    @Override
    public UserModel updateImage(UserRecordDto userRecordDto, UserModel userModel) {
        userModel.setImageUrl(userRecordDto.imageUrl());
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return userRepository.save(userModel);
    }

}
