package com.ead.courseservice.dtos;

import com.ead.courseservice.enums.UserStatus;
import com.ead.courseservice.enums.Usertype;

import java.util.UUID;

public record UserRecordDto(UUID userId,
                            String username,
                            String email,
                            String fullName,
                            UserStatus userStatus,
                            Usertype usertype,
                            String phoneNumber,
                            String imageUrl) {
}
