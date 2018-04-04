package com.frama.miserend.hu.home.pages.churches.filter;

import com.frama.miserend.hu.database.miserend.entities.Mass;
import com.frama.miserend.hu.utils.DateUtils;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Balazs on 2018. 02. 17..
 */

public class MassFilter {

    public static List<Mass> filterForDay(List<Mass> masses, LocalDate day) {
        List<Mass> filtered = new ArrayList<>();
        for (Mass mass : masses) {
            if (isMassOnDay(mass, day)) {
                filtered.add(mass);
            }
        }
        return filtered;
    }

    public static boolean isMassOnDay(Mass mass, LocalDate day) {
        return dayCorrect(mass, day) && dateRangeCorrect(mass, day);
    }

    private static boolean dayCorrect(Mass mass, LocalDate day) {
        return mass.getDay() == DateUtils.convertJavaDayToMassDay(day.getDayOfWeek().getValue()) || mass.getDay() == 0;
    }

    private static boolean dateRangeCorrect(Mass mass, LocalDate day) {
        int dayInDatabaseFormat = (day.getMonthValue()) * 100 + day.getDayOfMonth();
        if (mass.getFromDate() < mass.getToDate()) {
            return mass.getFromDate() <= dayInDatabaseFormat && dayInDatabaseFormat <= mass.getToDate();
        } else {
            return mass.getFromDate() <= dayInDatabaseFormat || dayInDatabaseFormat <= mass.getToDate();
        }
    }

}
