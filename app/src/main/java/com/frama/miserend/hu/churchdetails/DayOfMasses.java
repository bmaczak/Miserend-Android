package com.frama.miserend.hu.churchdetails;

import com.frama.miserend.hu.database.miserend.entities.Mass;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Balazs on 2018. 02. 21..
 */

public class DayOfMasses {
    private Calendar day;
    private List<Mass> masses;

    public DayOfMasses(Calendar day, List<Mass> masses) {
        this.day = day;
        this.masses = masses;
    }

    public Calendar getDay() {
        return day;
    }

    public List<Mass> getMasses() {
        return masses;
    }
}
