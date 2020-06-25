package com.andrei.sasu.backend.validation;

import com.andrei.sasu.backend.model.CreateAccountRequest;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ValidWorkingHoursValidator implements ConstraintValidator<ValidWorkingHours, CreateAccountRequest> {

    private final String validWorkingHours;
    private final String validWorkingDays;

    public ValidWorkingHoursValidator(@Value("${app.valid.working.hours}") String validWorkingHours,
                                      @Value("${app.valid.working.days}") String validWorkingDays) {
        this.validWorkingHours = validWorkingHours;
        this.validWorkingDays = validWorkingDays;
    }

    @Override
    public void initialize(ValidWorkingHours constraintAnnotation) {

    }

    @Override
    public boolean isValid(CreateAccountRequest createAccountRequest, ConstraintValidatorContext context) {

        try {
            final int startHour = getHours(validWorkingHours.split("-")[0]).get(Calendar.HOUR_OF_DAY);
            final int startMinute = getHours(validWorkingHours.split("-")[0]).get(Calendar.MINUTE);
            final int endHour = getHours(validWorkingHours.split("-")[1]).get(Calendar.HOUR_OF_DAY);
            final int endMinute = getHours(validWorkingHours.split("-")[1]).get(Calendar.MINUTE);

            final int startDay = getDayOfWeek(validWorkingDays.split("-")[0]);
            final int endDay = getDayOfWeek(validWorkingDays.split("-")[1]);


//            int currentHourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
//            int currentMinuteOfDay = Calendar.getInstance().get(Calendar.MINUTE);
            int currentDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

            final Calendar currentCalendar = Calendar.getInstance();
            final Calendar startCalendar = Calendar.getInstance();
            startCalendar.set(Calendar.HOUR_OF_DAY, startHour);
            startCalendar.set(Calendar.MINUTE, startMinute);

            final Calendar endCalendar = Calendar.getInstance();
            endCalendar.set(Calendar.HOUR_OF_DAY, endHour);
            endCalendar.set(Calendar.MINUTE, endMinute);


            return (currentDayOfWeek >= startDay && currentDayOfWeek <= endDay) && currentCalendar.after(startCalendar) && currentCalendar.before(endCalendar);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;

    }


    private int getDayOfWeek(final String day) throws ParseException {
        //http://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#patterns
        SimpleDateFormat dayFormat = new SimpleDateFormat("E");
        Date date = dayFormat.parse(day);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    private Calendar getHours(final String hours) throws ParseException {
        SimpleDateFormat dayFormat = new SimpleDateFormat("HH:mm");
        Date date = dayFormat.parse(hours);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}
