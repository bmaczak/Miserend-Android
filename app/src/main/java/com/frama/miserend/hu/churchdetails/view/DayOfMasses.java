package com.frama.miserend.hu.churchdetails.view;

import com.frama.miserend.hu.database.miserend.entities.Mass;

import org.threeten.bp.LocalDate;

import java.util.List;

/**
 * Created by Balazs on 2018. 02. 21..
 */

public class DayOfMasses {
    private LocalDate day;
    private List<Mass> masses;

    public DayOfMasses(LocalDate day, List<Mass> masses) {
        this.day = day;
        this.masses = masses;
    }

    public LocalDate getDay() {
        return day;
    }

    public List<Mass> getMasses() {
        return masses;
    }
}
