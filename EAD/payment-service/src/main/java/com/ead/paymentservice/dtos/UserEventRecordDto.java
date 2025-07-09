package com.ead.paymentservice.dtos;

import com.ead.paymentservice.models.UserModel;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

public record UserEventRecordDto(UUID userId,
                                 String username,
                                 String email,
                                 String fullName,
                                 String userStatus,
                                 String usertype,
                                 String phoneNumber,
                                 String imageUrl,
                                 String actionType) {

    public UserModel convertToUserModel() {
        var userModel = new UserModel();
        BeanUtils.copyProperties(this, userModel);
        return userModel;
    }
}
