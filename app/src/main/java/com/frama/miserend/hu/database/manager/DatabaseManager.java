package com.frama.miserend.hu.database.manager;

import android.content.Context;

import java.io.File;

/**
 * Created by Maczi on 2013.05.16..
 */
public class DatabaseManager {

    private Context context;

    public DatabaseManager(Context context) {
        this.context = context;
    }

    private static String TAG = "DatabaseManager";

    private static int DATABASE_VERSION = 4;

    public static String DATABASE_FILE_NAME = "miserend.sqlite3";

    public static final String DATABASE_URL = "http://miserend.hu/fajlok/sqlite/miserend_v" + DATABASE_VERSION + ".sqlite3";

    public boolean isDbExist() {
        return getDatabaseFile().exists();
    }

    public File getDatabaseFile() {
        return context.getDatabasePath(DATABASE_FILE_NAME);
    }

    public int getRequiredDataBaseVersion() {
        return DATABASE_VERSION;
    }
}
