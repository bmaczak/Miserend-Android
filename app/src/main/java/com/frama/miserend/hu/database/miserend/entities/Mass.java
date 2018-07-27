package com.frama.miserend.hu.database.miserend.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.frama.miserend.hu.utils.Validation;

import java.io.Serializable;

/**
 * Created by Balazs on 2018. 02. 10..
 */
@Entity(tableName = "misek", foreignKeys = @ForeignKey(entity = Church.class,
        parentColumns = "tid",
        childColumns = "tid"))
public class Mass implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "mid")
    private int id;

    @ColumnInfo(name = "tid")
    public int churchId;

    @ColumnInfo(name = "nap")
    private int day;

    @ColumnInfo(name = "ido")
    private String time;

    @ColumnInfo(name = "idoszak")
    private String season;

    @ColumnInfo(name = "nyelv")
    private String language;

    @ColumnInfo(name = "milyen")
    private String tags;

    @ColumnInfo(name = "periodus")
    private String period;

    @ColumnInfo(name = "suly")
    private int weight;

    @ColumnInfo(name = "datumtol")
    private int fromDate;

    @ColumnInfo(name = "datumig")
    private int toDate;

    @ColumnInfo(name = "megjegyzes")
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChurchId() {
        return churchId;
    }

    public void setChurchId(int churchId) {
        this.churchId = churchId;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getFromDate() {
        return fromDate;
    }

    public void setFromDate(int fromDate) {
        this.fromDate = fromDate;
    }

    public int getToDate() {
        return toDate;
    }

    public void setToDate(int toDate) {
        this.toDate = toDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean hasInfo() {
        return Validation.notEmpty(comment) || Validation.notEmpty(tags) || (period != null && !period.equals("0"));
    }
}
