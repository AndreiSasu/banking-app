package com.andrei.sasu.backend.validation;

import com.andrei.sasu.backend.model.CreateAccountRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;

public class ValidWorkingHoursValidator implements ConstraintValidator<ValidWorkingHours, CreateAccountRequest> {

    private final WorkingHours workingHours;

    public ValidWorkingHoursValidator(final WorkingHours workingHours) {
        this.workingHours = workingHours;
    }

    @Override
    public void initialize(ValidWorkingHours constraintAnnotation) {

    }

    @Override
    public boolean isValid(CreateAccountRequest createAccountRequest, ConstraintValidatorContext context) {

        int currentDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        final Calendar currentCalendar = Calendar.getInstance();
        final Calendar startCalendar = Calendar.getInstance();

        startCalendar.set(Calendar.HOUR_OF_DAY, workingHours.getStartHour());
        startCalendar.set(Calendar.MINUTE, workingHours.getStartMinute());

        final Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.HOUR_OF_DAY, workingHours.getEndHour());
        endCalendar.set(Calendar.MINUTE, workingHours.getEndMinute());

        return (currentDayOfWeek >= workingHours.getStartDayOfWeek() && currentDayOfWeek <= workingHours.getEndDayOfWeek())
                && currentCalendar.after(startCalendar) && currentCalendar.before(endCalendar);

    }
}



