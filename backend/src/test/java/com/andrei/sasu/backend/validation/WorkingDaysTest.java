package com.andrei.sasu.backend.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

public class WorkingDaysTest {

    @Test
    public void verifyDaysOutSideRange() {
        final WorkingDays workingDays = new WorkingDays(2, 6); //Mon-Fri
        LocalDateTime nextSunday = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

        Assertions.assertThat(workingDays.isOpen(nextSunday)).isFalse();

        LocalDateTime previousSunday = LocalDateTime.now().with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));

        Assertions.assertThat(workingDays.isOpen(previousSunday)).isFalse();
    }

    @Test
    public void verifyDaysWithinRange() {
        final WorkingDays workingDays = new WorkingDays(2, 6); //Mon-Fri
        LocalDateTime nextTuesday = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.TUESDAY));

        Assertions.assertThat(workingDays.isOpen(nextTuesday)).isTrue();

        LocalDateTime nextMonday = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));

        Assertions.assertThat(workingDays.isOpen(nextMonday)).isTrue();

        LocalDateTime nextFriday = LocalDateTime.now().with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));

        Assertions.assertThat(workingDays.isOpen(nextFriday)).isTrue();
    }
}
