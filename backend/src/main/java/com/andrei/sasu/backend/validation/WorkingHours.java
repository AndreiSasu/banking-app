package com.andrei.sasu.backend.validation;

import java.time.LocalDateTime;

public class WorkingHours {

    final int startHour;
    final int startMinute;

    final int endHour;
    final int endMinute;

    public WorkingHours(int startHour, int startMinute, int endHour, int endMinute) {
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
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

    /**
     * Verifies if given {@link LocalDateTime} is within business hours from a hours and minute perspective
     * Note: This does not take into account working days, use together with {@link WorkingDays}
     * @param localDateTime
     * @return
     */
    public boolean isOpen(final LocalDateTime localDateTime) {

        final LocalDateTime openTime = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(),
                localDateTime.getDayOfMonth(), startHour, startMinute);
        final LocalDateTime closingTime = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(),
                localDateTime.getDayOfMonth(), endHour, endMinute);

        return (localDateTime.isAfter(openTime) || localDateTime.isEqual(openTime)) &&
                (localDateTime.isBefore(closingTime) || localDateTime.isEqual(closingTime));
    }
}
