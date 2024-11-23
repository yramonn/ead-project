package com.ead.auth_service.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordConstraintImpl.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {
    String message() default """
            The password must contain: at least 8 characters, including one uppercase letter, one lowercase letter, 
            one number, and one special character (!@#$%^&*). Please try again.""";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
