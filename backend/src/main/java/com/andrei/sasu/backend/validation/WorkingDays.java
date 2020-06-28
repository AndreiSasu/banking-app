package com.andrei.sasu.backend.validation;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class WorkingDays {
    final DayOfWeek startDayOfWeek;
    final DayOfWeek endDayOfWeek;

    public WorkingDays(DayOfWeek startDayOfWeek, DayOfWeek endDayOfWeek) {
        this.startDayOfWeek = startDayOfWeek;
        this.endDayOfWeek = endDayOfWeek;
    }

    public DayOfWeek getStartDayOfWeek() {
        return startDayOfWeek;
    }

    public DayOfWeek getEndDayOfWeek() {
        return endDayOfWeek;
    }

    /**
     * Verifies if given {@link LocalDateTime} is within business hours from a working days perspective
     * Note: This does not take into account working hours, use together with {@link WorkingHours}
     * @param localDateTime
     * @return
     */
    public boolean isOpen(final LocalDateTime localDateTime) {
        return localDateTime.getDayOfWeek().getValue() >= startDayOfWeek.getValue() &&
                localDateTime.getDayOfWeek().getValue() <= endDayOfWeek.getValue();
    }
}
