package com.frama.miserend.hu;


import com.frama.miserend.hu.database.miserend.entities.Mass;
import com.frama.miserend.hu.home.pages.churches.filter.MassFilter;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by maczak on 2018. 02. 22..
 */

public class MassFilterTest {

    private List<Mass> masses;
    private Calendar calendar;

    @Before
    public void setup() {
        masses = new ArrayList<>();
        calendar = Calendar.getInstance();
    }

    @Test
    public void massFilter_CorrectForMonday() {
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Mass mass = new Mass();
        mass.setDay(1);
        setInfiniteInterval(mass);
        masses.add(mass);
        assertEquals(1, MassFilter.filterForDay(masses, calendar).size());
    }

    @Test
    public void massFilter_CorrectForTuesday() {
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        Mass mass = new Mass();
        mass.setDay(2);
        setInfiniteInterval(mass);
        masses.add(mass);
        assertEquals(1, MassFilter.filterForDay(masses, calendar).size());
    }

    @Test
    public void massFilter_CorrectForWednesday() {
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        Mass mass = new Mass();
        mass.setDay(3);
        setInfiniteInterval(mass);
        masses.add(mass);
        assertEquals(1, MassFilter.filterForDay(masses, calendar).size());
    }

    @Test
    public void massFilter_CorrectForThursday() {
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        Mass mass = new Mass();
        mass.setDay(4);
        setInfiniteInterval(mass);
        masses.add(mass);
        assertEquals(1, MassFilter.filterForDay(masses, calendar).size());
    }

    @Test
    public void massFilter_CorrectForFriday() {
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        Mass mass = new Mass();
        mass.setDay(5);
        setInfiniteInterval(mass);
        masses.add(mass);
        assertEquals(1, MassFilter.filterForDay(masses, calendar).size());
    }

    @Test
    public void massFilter_CorrectForSaturday() {
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        Mass mass = new Mass();
        mass.setDay(6);
        setInfiniteInterval(mass);
        masses.add(mass);
        assertEquals(1, MassFilter.filterForDay(masses, calendar).size());
    }

    @Test
    public void massFilter_CorrectForSunday() {
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        Mass mass = new Mass();
        mass.setDay(7);
        setInfiniteInterval(mass);
        masses.add(mass);
        assertEquals(1, MassFilter.filterForDay(masses, calendar).size());
    }

    @Test
    public void massFilter_InsideInterval() {
        calendar.set(Calendar.MONTH, 3);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Mass mass = new Mass();
        mass.setDay(0);
        mass.setFromDate(301);
        mass.setToDate(501);
        masses.add(mass);
        assertEquals(1, MassFilter.filterForDay(masses, calendar).size());
    }

    @Test
    public void massFilter_LeftBorderOfInterval() {
        calendar.set(Calendar.MONTH, 3);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Mass mass = new Mass();
        mass.setDay(0);
        mass.setFromDate(401);
        mass.setToDate(501);
        masses.add(mass);
        assertEquals(1, MassFilter.filterForDay(masses, calendar).size());
    }

    @Test
    public void massFilter_RightBorderOfInterval() {
        calendar.set(Calendar.MONTH, 3);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Mass mass = new Mass();
        mass.setDay(0);
        mass.setFromDate(301);
        mass.setToDate(401);
        masses.add(mass);
        assertEquals(1, MassFilter.filterForDay(masses, calendar).size());
    }

    @Test
    public void massFilter_OutsideInterval() {
        calendar.set(Calendar.MONTH, 3);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Mass mass = new Mass();
        mass.setDay(0);
        mass.setFromDate(601);
        mass.setToDate(701);
        masses.add(mass);
        assertEquals(0, MassFilter.filterForDay(masses, calendar).size());
    }

    @Test
    public void massFilter_OutsideIntervalOverYear() {
        calendar.set(Calendar.MONTH, 3);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Mass mass = new Mass();
        mass.setDay(0);
        mass.setFromDate(601);
        mass.setToDate(201);
        masses.add(mass);
        assertEquals(0, MassFilter.filterForDay(masses, calendar).size());
    }

    private void setInfiniteInterval(Mass mass) {
        mass.setFromDate(101);
        mass.setToDate(1231);
    }
}
