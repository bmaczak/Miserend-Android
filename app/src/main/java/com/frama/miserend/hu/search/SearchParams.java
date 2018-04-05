package com.frama.miserend.hu.search;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import java.io.Serializable;

/**
 * Created by Balazs on 2018. 03. 22..
 */

public class SearchParams implements Serializable {
    private String searchTerm;
    private boolean filterForMasses;
    private String city;
    private String churchName;
    private LocalDate date;
    private LocalTime fromTime;
    private LocalTime toTime;

    public SearchParams() {
        super();
    }

    public SearchParams(String searchTerm) {
        super();
        this.searchTerm = searchTerm;
    }

    public void normalize() {
        if (searchTerm == null) {
            searchTerm = "";
        }
        if (churchName == null) {
            churchName = "";
        }
        if (city == null) {
            city = "";
        }
    }


    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public boolean isFilterForMasses() {
        return filterForMasses;
    }

    public void setFilterForMasses(boolean filterForMasses) {
        this.filterForMasses = filterForMasses;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getChurchName() {
        return churchName;
    }

    public void setChurchName(String churchName) {
        this.churchName = churchName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getFromTime() {
        return fromTime;
    }

    public void setFromTime(LocalTime fromTime) {
        this.fromTime = fromTime;
    }

    public LocalTime getToTime() {
        return toTime;
    }

    public void setToTime(LocalTime toTime) {
        this.toTime = toTime;
    }
}
