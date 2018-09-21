package com.frama.miserend.hu.home.pages.churches.filter;

import com.frama.miserend.hu.database.miserend.entities.Mass;
import com.frama.miserend.hu.database.miserend.relations.MassWithChurch;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

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

    public static List<MassWithChurch> filterMassWithChurchForDay(List<MassWithChurch> masses, LocalDate day) {
        List<MassWithChurch> filtered = new ArrayList<>();
        for (MassWithChurch mass : masses) {
            if (isMassOnDay(mass.getMass(), day)) {
                filtered.add(mass);
            }
        }
        return filtered;
    }

    public static List<MassWithChurch> filterMassWithChurchForTime(List<MassWithChurch> masses, LocalTime from, LocalTime to) {
        List<MassWithChurch> filtered = new ArrayList<>();
        for (MassWithChurch mass : masses) {
            if (isBetween(mass.getMass(), from, to)) {
                filtered.add(mass);
            }
        }
        return filtered;
    }

    public static boolean isMassOnDay(Mass mass, LocalDate day) {
        return dayCorrect(mass, day) && dateRangeCorrect(mass, day);
    }

    private static boolean dayCorrect(Mass mass, LocalDate day) {
        return mass.getDay() == day.getDayOfWeek().getValue() || mass.getDay() == 0;
    }

    private static boolean isBetween(Mass mass, LocalTime from, LocalTime to) {
        LocalTime time = LocalTime.parse(mass.getTime());
        return !from.isAfter(time) && !to.isBefore(time);
    }

    private static boolean dateRangeCorrect(Mass mass, LocalDate day) {
        int dayInDatabaseFormat = (day.getMonthValue()) * 100 + day.getDayOfMonth();
        if (mass.getFromDate() < mass.getToDate()) {
            return mass.getFromDate() <= dayInDatabaseFormat && dayInDatabaseFormat <= mass.getToDate();
        } else if (mass.getFromDate() > mass.getToDate()){
            return mass.getFromDate() <= dayInDatabaseFormat || dayInDatabaseFormat <= mass.getToDate();
        } else {
            return mass.getFromDate() == dayInDatabaseFormat && dayInDatabaseFormat == mass.getToDate();
        }
    }

}
