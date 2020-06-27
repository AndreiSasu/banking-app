package com.andrei.sasu.backend.validation;

import com.andrei.sasu.backend.model.CreateAccountRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.util.Calendar;

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

//        final Calendar currentCalendar = Calendar.getInstance();
//        int currentDayOfWeek = currentCalendar.get(Calendar.DAY_OF_WEEK);
//
//        final Calendar startCalendar = Calendar.getInstance();
//
//        startCalendar.set(Calendar.HOUR_OF_DAY, workingHours.getStartHour());
//        startCalendar.set(Calendar.MINUTE, workingHours.getStartMinute());
//
//        final Calendar endCalendar = Calendar.getInstance();
//        endCalendar.set(Calendar.HOUR_OF_DAY, workingHours.getEndHour());
//        endCalendar.set(Calendar.MINUTE, workingHours.getEndMinute());
//
//        boolean isWorkingDay = (currentDayOfWeek >= workingHours.getStartDayOfWeek() && currentDayOfWeek <= workingHours.getEndDayOfWeek());
//        boolean isWorkingHours = currentCalendar.after(startCalendar) && currentCalendar.before(endCalendar);
        final LocalDateTime now = LocalDateTime.now();
        return workingDays.isOpen(now) && workingHours.isOpen(now);
    }
}



