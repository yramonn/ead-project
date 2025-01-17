package com.ead.auth_service.services.impl;

import com.ead.auth_service.dtos.UserRecordDto;
import com.ead.auth_service.enums.UserStatus;
import com.ead.auth_service.enums.Usertype;
import com.ead.auth_service.exceptions.NotFoundException;
import com.ead.auth_service.models.UserCourseModel;
import com.ead.auth_service.models.UserModel;
import com.ead.auth_service.repositories.UserCourseRepository;
import com.ead.auth_service.repositories.UserRepository;
import com.ead.auth_service.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;
    final UserCourseRepository userCourseRepository;

    public UserServiceImpl(UserRepository userRepository, UserCourseRepository userCourseRepository) {
        this.userRepository = userRepository;
        this.userCourseRepository = userCourseRepository;
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

    @Transactional
    @Override
    public void deleteUserById(UserModel userModel) {
        List<UserCourseModel> userCourseModelList =  userCourseRepository.findAllUserCourseIntoUser(userModel.getUserId());
        if(!userCourseModelList.isEmpty()) {
            userCourseRepository.deleteAll(userCourseModelList);
        }
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

    @Override
    public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }

    @Override
    public UserModel registerInstructor(UserModel userModel) {
        userModel.setUsertype(Usertype.INSTRUCTOR);
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return userRepository.save(userModel);
    }

}
