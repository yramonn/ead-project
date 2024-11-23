package com.ead.auth_service.dtos;

import com.ead.auth_service.validations.PasswordConstraint;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRecordDto(@NotBlank(groups = UserView.RegistrationPost.class, message = "Username is mandatory")
                            @Size(min = 4, max = 50, groups = UserView.RegistrationPost.class, message = "Size must be between 4 and 50")
                            @JsonView(UserView.RegistrationPost.class)
                            String username,

                            @NotBlank(groups = UserView.RegistrationPost.class, message = "Email is mandatory")
                            @Email(groups = UserView.RegistrationPost.class, message = "Email must be in the expected format")
                            @JsonView(UserView.RegistrationPost.class)
                            String email,

                            @NotBlank(groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class}, message = "Password is mandatory")
                            @Size(min = 6, max = 20, message = "Size must be between 6  and 20")
                            @PasswordConstraint(groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
                            @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
                            String password,

                            @NotBlank(groups = UserView.PasswordPut.class, message = "Password is mandatory")
                            @Size(groups = UserView.PasswordPut.class, min = 6, max = 20, message = "Size must be between 6  and 20")
                            @PasswordConstraint(groups = UserView.PasswordPut.class)
                            @JsonView(UserView.PasswordPut.class)
                            String oldPassword,

                            @NotBlank(groups = {UserView.RegistrationPost.class, UserView.UserPut.class}, message = "Full name is mandatory")
                            @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
                            String fullName,

                            @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
                            String phoneNumber,

                            @NotBlank(groups = UserView.ImagePut.class, message = "Image URL is mandatory")
                            @JsonView({UserView.RegistrationPost.class, UserView.ImagePut.class})
                            String imageUrl) {

    public interface UserView {
        interface RegistrationPost{}
        interface UserPut{}
        interface PasswordPut{}
        interface ImagePut{}
    }

}
