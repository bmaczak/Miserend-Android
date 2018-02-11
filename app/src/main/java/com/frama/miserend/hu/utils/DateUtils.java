package com.frama.miserend.hu.utils;

import android.content.Context;

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

    public static String getNameOfDay(Context context, int massDay) {
        int today = DateUtils.getTodayInMassDay();
        int tomorrow = DateUtils.getTomorrowInMassDay();
        if (massDay == today) {
            return context.getResources().getString(R.string.today);
        } else if (massDay == tomorrow) {
            return context.getResources().getString(R.string.tomorrow);
        } else {
            return context.getResources().getStringArray(
                    R.array.days_of_week)[massDay - 1];
        }

    }

    public static int getSundayInMassDay() {
        return 7;
    }
}
