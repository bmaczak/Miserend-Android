package com.frama.miserend.hu.database.miserend.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Balazs on 2018. 02. 10..
 */
@Entity(tableName = "templomok")
public class Church {

    @PrimaryKey
    private int tid;

    @ColumnInfo(name = "nev")
    private String name;

    @ColumnInfo(name = "ismertnev")
    private String commonName;

    @ColumnInfo(name = "gorog")
    private boolean greek;

    @ColumnInfo(name = "lat")
    private double lat;

    @ColumnInfo(name = "lng")
    private double lon;

    @ColumnInfo(name = "geocim")
    private String address;

    @ColumnInfo(name = "varos")
    private String city;

    @ColumnInfo(name = "orszag")
    private String country;

    @ColumnInfo(name = "megye")
    private String county;

    @ColumnInfo(name = "cim")
    private String street;

    @ColumnInfo(name = "megkozelites")
    private String gettingThere;

    @ColumnInfo(name = "kep")
    private String imageUrl;

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public boolean isGreek() {
        return greek;
    }

    public void setGreek(boolean greek) {
        this.greek = greek;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getGettingThere() {
        return gettingThere;
    }

    public void setGettingThere(String gettingThere) {
        this.gettingThere = gettingThere;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
