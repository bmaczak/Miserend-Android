package com.frama.miserend.hu.database.miserend.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Balazs on 2018. 02. 27..
 */
@Entity(tableName = "kepek", foreignKeys = @ForeignKey(entity = Church.class,
        parentColumns = "tid",
        childColumns = "tid"))
public class Image {

    @PrimaryKey
    @ColumnInfo(name = "kid")
    private int id;

    @ColumnInfo(name = "tid")
    private int churchId;

    @ColumnInfo(name = "kep")
    private String imageUrl;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
