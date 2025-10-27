package com.anthony;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
public class TimeFormatting {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");


    public static long spinnersToMillis(int hour, int minute){
        LocalTime time = LocalTime.of(hour, minute);
        return time.toSecondOfDay() * 1000L;
    }

    public static long timeStringToMillis(String timeString){
        LocalTime time = LocalTime.parse(timeString, TIME_FORMATTER);
        return time.toSecondOfDay() * 1000L;
    }

    public static String millisToTimeString(long millis) {
        LocalTime time = LocalTime.ofSecondOfDay(millis / 1000);
        return time.format(TIME_FORMATTER);
    }
}

