package com.frama.miserend.hu;


import com.frama.miserend.hu.database.miserend.entities.Mass;
import com.frama.miserend.hu.home.pages.churches.filter.MassFilter;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static junit.framework.Assert.assertEquals;

/**
 * Created by maczak on 2018. 02. 22..
 */

public class MassFilterTest {

    private Mass mass;
    private Calendar calendar;

    @Before
    public void setup() {
        mass = new Mass();
        calendar = Calendar.getInstance();
    }

    @Test
    public void massFilter_CorrectForMonday() {
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        mass.setDay(1);
        setInfiniteInterval(mass);
        assertEquals(true, MassFilter.isMassOnDay(mass, calendar));
    }

    @Test
    public void massFilter_CorrectForTuesday() {
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        mass.setDay(2);
        setInfiniteInterval(mass);
        assertEquals(true, MassFilter.isMassOnDay(mass, calendar));
    }

    @Test
    public void massFilter_CorrectForWednesday() {
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        mass.setDay(3);
        setInfiniteInterval(mass);
        assertEquals(true, MassFilter.isMassOnDay(mass, calendar));
    }

    @Test
    public void massFilter_CorrectForThursday() {
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        mass.setDay(4);
        setInfiniteInterval(mass);
        assertEquals(true, MassFilter.isMassOnDay(mass, calendar));
    }

    @Test
    public void massFilter_CorrectForFriday() {
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        mass.setDay(5);
        setInfiniteInterval(mass);
        assertEquals(true, MassFilter.isMassOnDay(mass, calendar));
    }

    @Test
    public void massFilter_CorrectForSaturday() {
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        mass.setDay(6);
        setInfiniteInterval(mass);
        assertEquals(true, MassFilter.isMassOnDay(mass, calendar));
    }

    @Test
    public void massFilter_CorrectForSunday() {
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        mass.setDay(7);
        setInfiniteInterval(mass);
        assertEquals(true, MassFilter.isMassOnDay(mass, calendar));
    }

    @Test
    public void massFilter_InsideInterval() {
        calendar.set(Calendar.MONTH, 3);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        mass.setDay(0);
        mass.setFromDate(301);
        mass.setToDate(501);
        assertEquals(true, MassFilter.isMassOnDay(mass, calendar));
    }

    @Test
    public void massFilter_LeftBorderOfInterval() {
        calendar.set(Calendar.MONTH, 3);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        mass.setDay(0);
        mass.setFromDate(401);
        mass.setToDate(501);
        assertEquals(true, MassFilter.isMassOnDay(mass, calendar));
    }

    @Test
    public void massFilter_RightBorderOfInterval() {
        calendar.set(Calendar.MONTH, 3);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        mass.setDay(0);
        mass.setFromDate(301);
        mass.setToDate(401);
        assertEquals(true, MassFilter.isMassOnDay(mass, calendar));
    }

    @Test
    public void massFilter_OutsideInterval() {
        calendar.set(Calendar.MONTH, 3);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        mass.setDay(0);
        mass.setFromDate(601);
        mass.setToDate(701);
        assertEquals(false, MassFilter.isMassOnDay(mass, calendar));
    }

    @Test
    public void massFilter_OutsideIntervalOverYear() {
        calendar.set(Calendar.MONTH, 3);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        mass.setDay(0);
        mass.setFromDate(601);
        mass.setToDate(201);
        assertEquals(false, MassFilter.isMassOnDay(mass, calendar));
    }

    private void setInfiniteInterval(Mass mass) {
        mass.setFromDate(101);
        mass.setToDate(1231);
    }
}
