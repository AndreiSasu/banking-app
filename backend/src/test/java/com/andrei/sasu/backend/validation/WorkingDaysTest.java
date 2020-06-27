package com.andrei.sasu.backend.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

public class WorkingDaysTest {

    @Test
    public void verifyDaysOutSideRange() {
        final WorkingDays workingDays = new WorkingDays(DayOfWeek.MONDAY, DayOfWeek.FRIDAY); //Mon-Fri
        LocalDateTime nextSunday = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

        Assertions.assertThat(workingDays.isOpen(nextSunday)).isFalse();

        LocalDateTime previousSunday = LocalDateTime.now().with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));

        Assertions.assertThat(workingDays.isOpen(previousSunday)).isFalse();
    }

    @Test
    public void verifyDaysWithinRange() {
        final WorkingDays workingDays = new WorkingDays(DayOfWeek.MONDAY, DayOfWeek.FRIDAY); //Mon-Fri
        LocalDateTime nextTuesday = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.TUESDAY));

        Assertions.assertThat(workingDays.isOpen(nextTuesday)).isTrue();

        LocalDateTime nextMonday = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));

        Assertions.assertThat(workingDays.isOpen(nextMonday)).isTrue();

        LocalDateTime previousFriday = LocalDateTime.now().with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));

        Assertions.assertThat(workingDays.isOpen(previousFriday)).isTrue();
    }

    @Test
    public void verifySingleDayRange() {
        final WorkingDays workingDays = new WorkingDays(DayOfWeek.WEDNESDAY, DayOfWeek.WEDNESDAY);

        LocalDateTime nextTuesday = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.TUESDAY));

        Assertions.assertThat(workingDays.isOpen(nextTuesday)).isFalse();

        LocalDateTime nextWednesday = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));

        Assertions.assertThat(workingDays.isOpen(nextWednesday)).isTrue();

        LocalDateTime previousWednesday = LocalDateTime.now().with(TemporalAdjusters.previous(DayOfWeek.WEDNESDAY));

        Assertions.assertThat(workingDays.isOpen(previousWednesday)).isTrue();
    }

    @Test
    public void testAllSevenDaysRange() {
        final WorkingDays workingDays = new WorkingDays(DayOfWeek.MONDAY, DayOfWeek.SUNDAY);

        Assertions.assertThat(List.of(LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)),
                LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.TUESDAY)),
                LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY)),
                LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.THURSDAY)),
                LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.FRIDAY)),
                LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)),
                LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY))
                ))
                .allSatisfy(localDateTime -> Assertions.assertThat(workingDays.isOpen(localDateTime)));
    }
}
