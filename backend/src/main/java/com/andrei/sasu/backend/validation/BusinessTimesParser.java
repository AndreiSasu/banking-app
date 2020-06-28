package com.andrei.sasu.backend.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BusinessTimesParser {

    //MONDAY-FRIDAY
    private static final Pattern REGEXP_DAYS_OF_WEEK_RANGE = Pattern.compile("^(SUNDAY|SATURDAY|MONDAY|TUESDAY|WEDNESDAY|THURSDAY|FRIDAY)-(SUNDAY|SATURDAY|MONDAY|TUESDAY|WEDNESDAY|THURSDAY|FRIDAY)$");

    // HH:mm-HH-mm
    private static final Pattern REGEXP_HOURS_RANGE = Pattern.compile("^((?:[01]\\d|2[0-3]):[0-5]\\d)-((?:[01]\\d|2[0-3]):[0-5]\\d)$");

    /**
     * Parses working days range.
     * @param workingDaysRange format must match MONDAY-SUNDAY pattern
     * @return {@link WorkingDays}
     */
    public static WorkingDays getWorkingDays(final String workingDaysRange) {

        //Mon, Fri
        final String[] daysOfWeekRange = getDaysOfWeekRange(workingDaysRange);

        final DayOfWeek startDayOfWeek = DayOfWeek.valueOf(daysOfWeekRange[0]);
        final DayOfWeek endDayOfWeek = DayOfWeek.valueOf(daysOfWeekRange[1]);

        return new WorkingDays(startDayOfWeek, endDayOfWeek);

    }

    /**
     * Parses working hours range.
     * @param workingHoursRange format must match HH:mm-HH:mm representing start hour and minute - end hour and minute.
     * @return  {@link WorkingHours}
     */
    public static WorkingHours getWorkingHours(final String workingHoursRange) {
        // HH:mm, HH:mm
        final String[] hoursRange = getHoursRange(workingHoursRange);

        try {
            final Calendar startTime = getHoursAsCalendar(hoursRange[0]);
            final int startHour = startTime.get(Calendar.HOUR_OF_DAY);
            final int startMinute = startTime.get(Calendar.MINUTE);

            final Calendar endTime = getHoursAsCalendar(hoursRange[1]);
            final int endHour = endTime.get(Calendar.HOUR_OF_DAY);
            final int endMinute = endTime.get(Calendar.MINUTE);

            return new WorkingHours(startHour, startMinute, endHour, endMinute);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static String[] getHoursRange(final String input) {
        Matcher matcher = REGEXP_HOURS_RANGE.matcher(input);
        if (!matcher.matches())
            throw new IllegalArgumentException(String.format("Hours range must match HH:mm-HH:mm format, got %s", input));
        final String[] hoursRange = new String[2];
        hoursRange[0] = matcher.group(1);
        hoursRange[1] = matcher.group(2);
        return hoursRange;
    }

    private static String[] getDaysOfWeekRange(final String input) {
        Matcher matcher = REGEXP_DAYS_OF_WEEK_RANGE.matcher(input);
        if (!matcher.matches())
            throw new IllegalArgumentException(String.format("Days of week range must match MONDAY-FRIDAY format, got %s", input));
        final String[] hoursRange = new String[2];
        hoursRange[0] = matcher.group(1);
        hoursRange[1] = matcher.group(2);
        return hoursRange;
    }

    private static Calendar getHoursAsCalendar(final String hours) throws ParseException {
        SimpleDateFormat dayFormat = new SimpleDateFormat("HH:mm");
        Date date = dayFormat.parse(hours);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}
