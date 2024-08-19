package com.dardanqsot.eval.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;


public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {

    @Value("${password.format}")
    private String passwordFormat;

    @Value("${password.message}")
    private String passwordMessage;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        boolean isValid = password != null && password.matches(passwordFormat);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(passwordMessage)
                    .addConstraintViolation();
        }

        return isValid;
    }
}
