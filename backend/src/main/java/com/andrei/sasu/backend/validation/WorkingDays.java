package com.andrei.sasu.backend.validation;

import java.time.LocalDateTime;

public class WorkingDays {
    final int startDayOfWeek;
    final int endDayOfWeek;

    public WorkingDays(int startDayOfWeek, int endDayOfWeek) {
        this.startDayOfWeek = startDayOfWeek;
        this.endDayOfWeek = endDayOfWeek;
    }

    public int getStartDayOfWeek() {
        return startDayOfWeek;
    }

    public int getEndDayOfWeek() {
        return endDayOfWeek;
    }

    public boolean isOpen(final LocalDateTime localDateTime) {
        return localDateTime.getDayOfWeek().getValue() >= startDayOfWeek &&
                localDateTime.getDayOfWeek().getValue() <= endDayOfWeek;
    }
}
