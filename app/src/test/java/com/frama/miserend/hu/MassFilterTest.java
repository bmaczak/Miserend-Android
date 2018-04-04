package com.frama.miserend.hu;


import com.frama.miserend.hu.database.miserend.entities.Mass;
import com.frama.miserend.hu.home.pages.churches.filter.MassFilter;

import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;

import static junit.framework.Assert.assertEquals;

/**
 * Created by maczak on 2018. 02. 22..
 */

public class MassFilterTest {

    private Mass mass;
    private LocalDate date;

    @Before
    public void setup() {
        mass = new Mass();
        date = LocalDate.now();
    }

    @Test
    public void massFilter_CorrectForMonday() {
        date = LocalDate.now().with(DayOfWeek.MONDAY);
        mass.setDay(1);
        setInfiniteInterval(mass);
        assertEquals(true, MassFilter.isMassOnDay(mass, date));
    }

    @Test
    public void massFilter_CorrectForTuesday() {
        date = LocalDate.now().with(DayOfWeek.TUESDAY);
        mass.setDay(2);
        setInfiniteInterval(mass);
        assertEquals(true, MassFilter.isMassOnDay(mass, date));
    }

    @Test
    public void massFilter_CorrectForWednesday() {
        date = LocalDate.now().with(DayOfWeek.WEDNESDAY);
        mass.setDay(3);
        setInfiniteInterval(mass);
        assertEquals(true, MassFilter.isMassOnDay(mass, date));
    }

    @Test
    public void massFilter_CorrectForThursday() {
        date = LocalDate.now().with(DayOfWeek.THURSDAY);
        mass.setDay(4);
        setInfiniteInterval(mass);
        assertEquals(true, MassFilter.isMassOnDay(mass, date));
    }

    @Test
    public void massFilter_CorrectForFriday() {
        date = LocalDate.now().with(DayOfWeek.FRIDAY);
        mass.setDay(5);
        setInfiniteInterval(mass);
        assertEquals(true, MassFilter.isMassOnDay(mass, date));
    }

    @Test
    public void massFilter_CorrectForSaturday() {
        date = LocalDate.now().with(DayOfWeek.SATURDAY);
        mass.setDay(6);
        setInfiniteInterval(mass);
        assertEquals(true, MassFilter.isMassOnDay(mass, date));
    }

    @Test
    public void massFilter_CorrectForSunday() {
        date = LocalDate.now().with(DayOfWeek.SUNDAY);
        mass.setDay(7);
        setInfiniteInterval(mass);
        assertEquals(true, MassFilter.isMassOnDay(mass, date));
    }

    @Test
    public void massFilter_InsideInterval() {
        date = LocalDate.now().with(DayOfWeek.MONDAY);
        mass.setDay(0);
        mass.setFromDate(301);
        mass.setToDate(501);
        assertEquals(true, MassFilter.isMassOnDay(mass, date));
    }

    @Test
    public void massFilter_LeftBorderOfInterval() {
        date = LocalDate.of(2018, 3, 1);
        mass.setDay(0);
        mass.setFromDate(401);
        mass.setToDate(501);
        assertEquals(true, MassFilter.isMassOnDay(mass, date));
    }

    @Test
    public void massFilter_RightBorderOfInterval() {
        date = LocalDate.of(2018, 3, 1);
        mass.setDay(0);
        mass.setFromDate(301);
        mass.setToDate(401);
        assertEquals(true, MassFilter.isMassOnDay(mass, date));
    }

    @Test
    public void massFilter_OutsideInterval() {
        date = LocalDate.of(2018, 3, 1);
        mass.setDay(0);
        mass.setFromDate(601);
        mass.setToDate(701);
        assertEquals(false, MassFilter.isMassOnDay(mass, date));
    }

    @Test
    public void massFilter_OutsideIntervalOverYear() {
        date = LocalDate.of(2018, 3, 1);
        mass.setDay(0);
        mass.setFromDate(601);
        mass.setToDate(201);
        assertEquals(false, MassFilter.isMassOnDay(mass, date));
    }

    private void setInfiniteInterval(Mass mass) {
        mass.setFromDate(101);
        mass.setToDate(1231);
    }
}
