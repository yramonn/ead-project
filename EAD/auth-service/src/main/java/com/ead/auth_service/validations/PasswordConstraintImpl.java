package com.ead.auth_service.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordConstraintImpl implements ConstraintValidator<PasswordConstraint, String> {

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{6,20}$";

    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        if(password == null || password.trim().isEmpty() || password.contains(" ")) {
            return false;
        } else return pattern.matcher(password).matches();
    }
}
