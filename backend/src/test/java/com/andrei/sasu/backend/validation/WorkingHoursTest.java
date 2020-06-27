package com.andrei.sasu.backend.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class WorkingHoursTest {

    private static final WorkingHours normalWorkingHours = new WorkingHours(9, 0, 18, 0);
    private static final WorkingHours nonStopWorkingHours = new WorkingHours(0, 0, 23, 59);

    @Test
    public void testWithinWorkingHoursHappyPath() {
        Assertions.assertThat(normalWorkingHours.isOpen(LocalDateTime.of(LocalDate.now(), LocalTime.of(9,15)))).isTrue();
        Assertions.assertThat(normalWorkingHours.isOpen(LocalDateTime.of(LocalDate.now(), LocalTime.of(9,0)))).isTrue();
        Assertions.assertThat(normalWorkingHours.isOpen(LocalDateTime.of(LocalDate.now(), LocalTime.of(18,0)))).isTrue();
        Assertions.assertThat(normalWorkingHours.isOpen(LocalDateTime.of(LocalDate.now(), LocalTime.of(12,00)))).isTrue();

    }

    @Test
    public void testNotWithinWorkingHours() {
        Assertions.assertThat(normalWorkingHours.isOpen(LocalDateTime.of(LocalDate.now(), LocalTime.of(18,15)))).isFalse();
        Assertions.assertThat(normalWorkingHours.isOpen(LocalDateTime.of(LocalDate.now(), LocalTime.of(8,59)))).isFalse();
    }

    @Test
    public void testNonStopWorkingHours() {

        Assertions.assertThat(List.of(LocalDateTime.of(LocalDate.now(), LocalTime.of(9,15)),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(9,0)),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(18,0)),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(12,00)),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(18,15)),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(8,59)),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0)),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59))
                )).allSatisfy(localDateTime -> {
                    Assertions.assertThat(nonStopWorkingHours.isOpen(localDateTime)).isTrue();
        });
    }
}
