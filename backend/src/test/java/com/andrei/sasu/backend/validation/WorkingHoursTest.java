package com.andrei.sasu.backend.validation;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

public class WorkingHoursTest {

    @Test
    public void testInvalidWorkingHoursRange() {
        final Throwable throwable = Assertions.catchThrowable(() -> new WorkingHours("25:01-00:20", "Mon-Fri"));
        Assertions.assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Hours range must match HH:mm-HH:mm format, got 25:01-00:20");
    }

    @Test
    public void testValidWorkingHoursHappyPath() throws ParseException {
        final WorkingHours workingHours = new WorkingHours("09:15-17:00", "Mon-Fri");
        Assertions.assertThat(workingHours.getStartHour())
                .isEqualTo(9);
        Assertions.assertThat(workingHours.getStartMinute())
                .isEqualTo(15);
        Assertions.assertThat(workingHours.getEndHour())
                .isEqualTo(17);
        Assertions.assertThat(workingHours.getEndMinute())
                .isEqualTo(0);

        Assertions.assertThat(workingHours.getStartDayOfWeek()).isEqualTo(2); //Mon
        Assertions.assertThat(workingHours.getEndDayOfWeek()).isEqualTo(6); //Fri
    }

    @Test
    public void testInvalidWorkingDays()  {
        final Throwable throwable = Assertions.catchThrowable(() ->  new WorkingHours("09:15-17:00", "Today-Tomorrow"));
        Assertions.assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Days of week range must match Mon-Fri format, got Today-Tomorrow");
    }
}
