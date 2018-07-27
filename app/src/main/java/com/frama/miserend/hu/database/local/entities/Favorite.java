package com.frama.miserend.hu.database.local.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by maczak on 2018. 02. 16..
 */

@Entity(tableName = "favorites")
public class Favorite {

    @PrimaryKey
    @ColumnInfo(name = "tid")
    private int churchId;

    public Favorite(int churchId) {
        this.churchId = churchId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Favorite) {
            return ((Favorite) obj).getChurchId() == getChurchId();
        } else {
            return super.equals(obj);
        }
    }

    public int getChurchId() {
        return churchId;
    }

    public void setChurchId(int churchId) {
        this.churchId = churchId;
    }
}
