package com.dardanqsot.eval.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {PasswordValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {


	String message() default "Password incorrecto";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
