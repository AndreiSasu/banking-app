package com.andrei.sasu.backend.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {OneSavingsAccountValidator.class})
public @interface OneSavingsAccountPerUser {
    String message() default "Only one savings account is allowed per user.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
