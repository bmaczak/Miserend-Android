package com.frama.miserend.hu.utils;

import android.content.res.Resources;

import com.frama.miserend.hu.R;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    public static int convertCalendarDayToMassDay(int calendarDay) {
        return (calendarDay + 5) % 7 + 1;
    }

    public static String getNameOfDay(Resources resources, Calendar calendar) {
        long daysBetween = daysBetween(calendar, Calendar.getInstance());
        if (daysBetween == 0) {
            return resources.getString(R.string.today);
        } else if (daysBetween == 1) {
            return resources.getString(R.string.tomorrow);
        } else {
            int massDay = convertCalendarDayToMassDay(calendar.get(Calendar.DAY_OF_WEEK));
            return resources.getStringArray(
                    R.array.days_of_week)[massDay - 1];
        }
    }

    public static long daysBetween(Calendar startDate, Calendar endDate) {

        //Make sure we don't change the parameter passed
        Calendar newStart = Calendar.getInstance();
        newStart.setTimeInMillis(startDate.getTimeInMillis());
        newStart.set(Calendar.HOUR_OF_DAY, 0);
        newStart.set(Calendar.MINUTE, 0);
        newStart.set(Calendar.SECOND, 0);
        newStart.set(Calendar.MILLISECOND, 0);

        Calendar newEnd = Calendar.getInstance();
        newEnd.setTimeInMillis(endDate.getTimeInMillis());
        newEnd.set(Calendar.HOUR_OF_DAY, 0);
        newEnd.set(Calendar.MINUTE, 0);
        newEnd.set(Calendar.SECOND, 0);
        newEnd.set(Calendar.MILLISECOND, 0);

        long end = newEnd.getTimeInMillis();
        long start = newStart.getTimeInMillis();
        return TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
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
