package com.frama.miserend.hu.preferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Balazs on 2018. 03. 04..
 */

public class Preferences {

    private static final String PREFERENCE_FILE_KEY = "com.frama.miserend.hu.preferences.PREFERENCE_FILE_KEY";

    private static final String DATABASE_LAST_UPDATED = "DATABASE_LAST_UPDATED";

    private static final String SAVED_DATABASE_VERSION = "SAVED_DATABASE_VERSION";


    private SharedPreferences sharedPreferences;

    public Preferences(Context context) {
        sharedPreferences = context.getSharedPreferences(
                PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
    }

    public void setDatabaseLastUpdated(long time) {
        putLong(DATABASE_LAST_UPDATED, time);
    }

    public long getDatabaseLastUpdated() {
        return getLong(DATABASE_LAST_UPDATED, 0);
    }

    public void setSavedDatabaseVersion(int version) {
        putInt(SAVED_DATABASE_VERSION, version);
    }

    public int getSavedDatabseVersion() {
        return getInt(SAVED_DATABASE_VERSION, 4);
    }

    @SuppressLint("ApplySharedPref")
    private void putLong(String key, long value) {
        sharedPreferences.edit().putLong(key, value).commit();
    }

    private long getLong(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    @SuppressLint("ApplySharedPref")
    private void putInt(String key, int value) {
        sharedPreferences.edit().putInt(key, value).commit();
    }

    private int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }
}
