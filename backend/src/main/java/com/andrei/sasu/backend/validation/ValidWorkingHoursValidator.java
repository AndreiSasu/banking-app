package com.andrei.sasu.backend.validation;

import com.andrei.sasu.backend.model.CreateAccountRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class ValidWorkingHoursValidator implements ConstraintValidator<ValidWorkingHours, CreateAccountRequest> {

    private final WorkingDays workingDays;
    private final WorkingHours workingHours;

    public ValidWorkingHoursValidator(final WorkingDays workingDays, final WorkingHours workingHours) {
        this.workingDays = workingDays;
        this.workingHours = workingHours;
    }

    @Override
    public void initialize(ValidWorkingHours constraintAnnotation) {

    }

    @Override
    public boolean isValid(CreateAccountRequest createAccountRequest, ConstraintValidatorContext context) {
        final LocalDateTime now = LocalDateTime.now();
        return workingDays.isOpen(now) && workingHours.isOpen(now);
    }
}



