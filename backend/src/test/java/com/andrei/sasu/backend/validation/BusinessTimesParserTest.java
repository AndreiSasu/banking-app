package com.andrei.sasu.backend.validation;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.time.DayOfWeek;

public class BusinessTimesParserTest {

    @Test
    public void testInvalidWorkingHoursRange() {
        final Throwable throwable = Assertions.catchThrowable(() -> BusinessTimesParser.getWorkingHours("25:01-00:20"));
        Assertions.assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Hours range must match HH:mm-HH:mm format, got 25:01-00:20");
    }

    @Test
    public void testValidWorkingHoursHappyPath() throws ParseException {
        WorkingHours workingHours = BusinessTimesParser.getWorkingHours("09:15-17:00");
        Assertions.assertThat(workingHours.getStartHour())
                .isEqualTo(9);
        Assertions.assertThat(workingHours.getStartMinute())
                .isEqualTo(15);
        Assertions.assertThat(workingHours.getEndHour())
                .isEqualTo(17);
        Assertions.assertThat(workingHours.getEndMinute())
                .isEqualTo(0);

        workingHours = BusinessTimesParser.getWorkingHours("00:00-23:59");
        Assertions.assertThat(workingHours.getStartHour())
                .isEqualTo(0);
        Assertions.assertThat(workingHours.getStartMinute())
                .isEqualTo(0);
        Assertions.assertThat(workingHours.getEndHour())
                .isEqualTo(23);
        Assertions.assertThat(workingHours.getEndMinute())
                .isEqualTo(59);
    }

    @Test
    public void testInvalidWorkingDays()  {
        final Throwable throwable = Assertions.catchThrowable(() ->  BusinessTimesParser.getWorkingDays( "Today-Tomorrow"));
        Assertions.assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Days of week range must match MONDAY-FRIDAY format, got Today-Tomorrow");
    }

    @Test
    public void testWorkingDaysHappyPath() {
        WorkingDays workingDays = BusinessTimesParser.getWorkingDays("MONDAY-FRIDAY");
        Assertions.assertThat(workingDays.getStartDayOfWeek()).isEqualTo(DayOfWeek.MONDAY);
        Assertions.assertThat(workingDays.getEndDayOfWeek()).isEqualTo(DayOfWeek.FRIDAY);
        // 7 days / week
        workingDays = BusinessTimesParser.getWorkingDays("MONDAY-SUNDAY");

        Assertions.assertThat(workingDays.getStartDayOfWeek()).isEqualTo(DayOfWeek.MONDAY);
        Assertions.assertThat(workingDays.getEndDayOfWeek()).isEqualTo(DayOfWeek.SUNDAY);
    }
}
