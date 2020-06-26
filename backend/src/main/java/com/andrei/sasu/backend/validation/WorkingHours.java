package com.andrei.sasu.backend.validation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class WorkingHours {

    final int startHour;
    final int startMinute;
    final int endHour;
    final int endMinute;

    final int startDayOfWeek;
    final int endDayOfWeek;

    public WorkingHours(@Value("${app.valid.working.hours}") String validWorkingHours,
                        @Value("${app.valid.working.days}") String validWorkingDays) throws ParseException {
        startHour = getHours(validWorkingHours.split("-")[0]).get(Calendar.HOUR_OF_DAY);
        startMinute = getHours(validWorkingHours.split("-")[0]).get(Calendar.MINUTE);;
        endHour = getHours(validWorkingHours.split("-")[1]).get(Calendar.HOUR_OF_DAY);
        endMinute = getHours(validWorkingHours.split("-")[1]).get(Calendar.MINUTE);
        startDayOfWeek = getDayOfWeek(validWorkingDays.split("-")[0]);
        endDayOfWeek = getDayOfWeek(validWorkingDays.split("-")[1]);
    }

    public int getStartHour() {
        return startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public int getStartDayOfWeek() {
        return startDayOfWeek;
    }

    public int getEndDayOfWeek() {
        return endDayOfWeek;
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
