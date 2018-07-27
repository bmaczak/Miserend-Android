package com.frama.miserend.hu.database.local.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;

/**
 * Created by maczak on 2018. 02. 16..
 */

@Entity(tableName = "recent_searches")
public class RecentSearch {

    @PrimaryKey
    @ColumnInfo(name = "timestamp")
    private long timestamp;

    @ColumnInfo(name = "searchterm")
    private String searchTerm;

    public RecentSearch(String searchTerm) {
        this.searchTerm = searchTerm;
        this.timestamp = Calendar.getInstance().getTimeInMillis();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
}
