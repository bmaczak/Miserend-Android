package com.frama.miserend.hu.utils;

import android.content.res.Resources;

import com.frama.miserend.hu.R;

import org.threeten.bp.LocalDate;

import static org.threeten.bp.temporal.ChronoUnit.DAYS;

public class DateUtils {

    public static String getNameOfDay(Resources resources, LocalDate day) {
        long daysBetween = daysBetween(LocalDate.now(), day);
        if (daysBetween == 0) {
            return resources.getString(R.string.today);
        } else if (daysBetween == 1) {
            return resources.getString(R.string.tomorrow);
        } else {
            int massDay = day.getDayOfWeek().getValue();
            return resources.getStringArray(
                    R.array.days_of_week)[massDay - 1];
        }
    }

    public static long daysBetween(LocalDate startDate, LocalDate endDate) {
        return DAYS.between(startDate, endDate);
    }

    public static String cutSecondsFromTime(String time) {
        if (null != time && time.length() > 0) {
            int endIndex = time.lastIndexOf(":");
            if (endIndex != -1) {
                return time.substring(0, endIndex);
            } else {
                return time;
            }
        } else {
            return time;
        }
    }

}
