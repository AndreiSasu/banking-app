package com.andrei.sasu.backend.validation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class WorkingHours {


    final int startHour;
    final int startMinute;

    final int endHour;
    final int endMinute;

    final int startDayOfWeek;
    final int endDayOfWeek;

    // HH:mm-HH-mm
    private static final String REGEXP_HOURS_RANGE = "^((?:[01]\\d|2[0-3]):[0-5]\\d)-((?:[01]\\d|2[0-3]):[0-5]\\d)$";

    //Mon-Fri
    private static final String REGEXP_DAYS_OF_WEEK_RANGE = "^(Sun|Mon|Tue|Wed|Thu|Fri)-(Sun|Mon|Tue|Wed|Thu|Fri)$";

    public WorkingHours(@Value("${app.valid.working.hours}") String validWorkingHours,
                        @Value("${app.valid.working.days}") String validWorkingDays) throws ParseException {
        // HH:mm, HH-mm
        final String[] hoursRange = getHoursRange(validWorkingHours);

        //Mon, Fri
        final String[] daysOfWeekRange = getDaysOfWeekRange(validWorkingDays);

        startHour = getCalendar(hoursRange[0]).get(Calendar.HOUR_OF_DAY);
        startMinute = getCalendar(hoursRange[0]).get(Calendar.MINUTE);

        endHour = getCalendar(hoursRange[1]).get(Calendar.HOUR_OF_DAY);
        endMinute = getCalendar(hoursRange[1]).get(Calendar.MINUTE);

        startDayOfWeek = getDayOfWeek(daysOfWeekRange[0]);
        endDayOfWeek = getDayOfWeek(daysOfWeekRange[1]);
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


    private String[] getHoursRange(final String input) {
        Pattern pattern = Pattern.compile(REGEXP_HOURS_RANGE);
        Matcher matcher = pattern.matcher(input);
        if(!matcher.matches()) throw new IllegalArgumentException(String.format("Hours range must match HH:mm-HH:mm format, got %s", input));
        final String[] hoursRange = new String[2];
        hoursRange[0] = matcher.group(1);
        hoursRange[1] = matcher.group(2);
        return hoursRange;
    }

    private String[] getDaysOfWeekRange(final String input) {
        Pattern pattern = Pattern.compile(REGEXP_DAYS_OF_WEEK_RANGE);
        Matcher matcher = pattern.matcher(input);
        if(!matcher.matches()) throw new IllegalArgumentException(String.format("Days of week range must match Mon-Fri format, got %s", input));
        final String[] hoursRange = new String[2];
        hoursRange[0] = matcher.group(1);
        hoursRange[1] = matcher.group(2);
        return hoursRange;
    }

    //http://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#patterns
    private int getDayOfWeek(final String day) throws ParseException {
        SimpleDateFormat dayFormat = new SimpleDateFormat("E");
        Date date = dayFormat.parse(day);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    private Calendar getCalendar(final String hours) throws ParseException {
        SimpleDateFormat dayFormat = new SimpleDateFormat("HH:mm");
        Date date = dayFormat.parse(hours);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}
