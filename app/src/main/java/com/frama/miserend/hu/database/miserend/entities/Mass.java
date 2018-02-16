package com.frama.miserend.hu.database.miserend.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Balazs on 2018. 02. 10..
 */
@Entity(tableName = "misek", foreignKeys = @ForeignKey(entity = Church.class,
        parentColumns = "tid",
        childColumns = "tid"))
public class Mass {

    @PrimaryKey
    private int mid;

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

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
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
}
