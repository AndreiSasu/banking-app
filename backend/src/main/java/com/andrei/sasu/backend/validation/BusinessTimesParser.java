package com.andrei.sasu.backend.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BusinessTimesParser {

    //Mon-Fri
    private static final Pattern REGEXP_DAYS_OF_WEEK_RANGE = Pattern.compile("^(Sun|Sat|Mon|Tue|Wed|Thu|Fri)-(Sun|Sat|Mon|Tue|Wed|Thu|Fri)$");

    // HH:mm-HH-mm
    private static final Pattern REGEXP_HOURS_RANGE = Pattern.compile("^((?:[01]\\d|2[0-3]):[0-5]\\d)-((?:[01]\\d|2[0-3]):[0-5]\\d)$");

    public static WorkingDays getWorkingDays(final String workingDaysRange)  {

        //Mon, Fri
        final String[] daysOfWeekRange = getDaysOfWeekRange(workingDaysRange);

        try {
            final int startDayOfWeek = getDayOfWeek(daysOfWeekRange[0]);
            final int endDayOfWeek = getDayOfWeek(daysOfWeekRange[1]);

            return new WorkingDays(startDayOfWeek, endDayOfWeek);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static WorkingHours getWorkingHours(final String workingHoursRange) {
        // HH:mm, HH-mm
        final String[] hoursRange = getHoursRange(workingHoursRange);

        try {
            final int startHour = getCalendar(hoursRange[0]).get(Calendar.HOUR_OF_DAY);
            final int startMinute = getCalendar(hoursRange[0]).get(Calendar.MINUTE);

            final int endHour = getCalendar(hoursRange[1]).get(Calendar.HOUR_OF_DAY);
            final int endMinute = getCalendar(hoursRange[1]).get(Calendar.MINUTE);

            return new WorkingHours(startHour, startMinute, endHour, endMinute);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static String[] getHoursRange(final String input) {
        Matcher matcher = REGEXP_HOURS_RANGE.matcher(input);
        if(!matcher.matches()) throw new IllegalArgumentException(String.format("Hours range must match HH:mm-HH:mm format, got %s", input));
        final String[] hoursRange = new String[2];
        hoursRange[0] = matcher.group(1);
        hoursRange[1] = matcher.group(2);
        return hoursRange;
    }

    private static String[] getDaysOfWeekRange(final String input) {
        Matcher matcher = REGEXP_DAYS_OF_WEEK_RANGE.matcher(input);
        if(!matcher.matches()) throw new IllegalArgumentException(String.format("Days of week range must match Mon-Fri format, got %s", input));
        final String[] hoursRange = new String[2];
        hoursRange[0] = matcher.group(1);
        hoursRange[1] = matcher.group(2);
        return hoursRange;
    }

    //http://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#patterns
    private static int getDayOfWeek(final String day) throws ParseException {
        SimpleDateFormat dayFormat = new SimpleDateFormat("E");
        Date date = dayFormat.parse(day);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    private static Calendar getCalendar(final String hours) throws ParseException {
        SimpleDateFormat dayFormat = new SimpleDateFormat("HH:mm");
        Date date = dayFormat.parse(hours);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}
