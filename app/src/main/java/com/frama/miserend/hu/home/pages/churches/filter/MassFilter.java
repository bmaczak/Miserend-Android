package com.frama.miserend.hu.home.pages.churches.filter;

import com.frama.miserend.hu.database.miserend.entities.Mass;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Balazs on 2018. 02. 17..
 */

public class MassFilter {

    public static List<Mass> filterForDay(List<Mass> masses, Calendar day) {
        List<Mass> filtered = new ArrayList<>();
        for (Mass mass : masses) {
            if (dayCorrect(mass, day) && dateRangeCorrect(mass, day)) {
                filtered.add(mass);
            }
        }
        return filtered;
    }

    private static boolean dayCorrect(Mass mass, Calendar day) {
        return mass.getDay() == day.get(Calendar.DAY_OF_WEEK) - 1 || mass.getDay() == 0;
    }

    private static boolean dateRangeCorrect(Mass mass, Calendar day) {
        int dayInDatabaseFormat = (day.get(Calendar.MONTH) + 1) * 100 + day.get(Calendar.DAY_OF_MONTH);
        if (mass.getFromDate() < mass.getToDate()) {
            return mass.getFromDate() <= dayInDatabaseFormat && dayInDatabaseFormat <= mass.getToDate();
        } else {
            return mass.getFromDate() >= dayInDatabaseFormat || dayInDatabaseFormat >= mass.getToDate();
        }
    }

}
