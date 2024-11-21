package com.ead.auth_service.dtos;

import com.fasterxml.jackson.annotation.JsonView;

public record UserRecordDto(@JsonView(UserView.RegistrationPost.class)
                            String username,

                            @JsonView(UserView.RegistrationPost.class)
                            String email,

                            @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
                            String password,

                            @JsonView(UserView.PasswordPut.class)
                            String oldPassword,

                            @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
                            String fullName,

                            @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
                            String phoneNumber,

                            @JsonView({UserView.RegistrationPost.class, UserView.ImagePut.class})
                            String imageUrl) {
    public interface UserView {
        interface RegistrationPost{}
        interface UserPut{}
        interface PasswordPut{}
        interface ImagePut{}
    }

}
