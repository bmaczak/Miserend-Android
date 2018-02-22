package com.frama.miserend.hu.utils;

import android.content.Context;
import android.content.res.Resources;

import com.frama.miserend.hu.R;

import java.util.Calendar;

public class DateUtils {

    public static int convertCalendarDayToMassDay(int calendarDay) {
        return (calendarDay + 5) % 7 + 1;
    }

    public static int getTodayInMassDay() {
        return convertCalendarDayToMassDay(Calendar.getInstance().get(
                Calendar.DAY_OF_WEEK));
    }

    public static int getTomorrowInMassDay() {
        return convertCalendarDayToMassDay(Calendar.getInstance().get(
                Calendar.DAY_OF_WEEK) + 1);
    }

    public static String getNameOfDay(Resources resources, int massDay) {
        int today = DateUtils.getTodayInMassDay();
        int tomorrow = DateUtils.getTomorrowInMassDay();
        if (massDay == today) {
            return resources.getString(R.string.today);
        } else if (massDay == tomorrow) {
            return resources.getString(R.string.tomorrow);
        } else {
            return resources.getStringArray(
                    R.array.days_of_week)[massDay - 1];
        }
    }
}
