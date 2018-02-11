package com.frama.miserend.hu.database;

import android.content.Context;

import java.io.File;

/**
 * Created by Maczi on 2013.05.16..
 */
public class DatabaseManager {

    private static String TAG = "DatabaseManager";

    private static int DATABASE_VERSION = 4;

    private static String DATABASE_FILE_NAME = "miserend.sqlite3";

    public static final String DATABASE_URL = "http://miserend.hu/fajlok/sqlite/miserend_v" + DATABASE_VERSION + ".sqlite3";

    public static boolean isDbExist(Context context) {
        return getDatabaseFile(context).exists();
    }

    public static File getDatabaseFile(Context context) {
        return context.getDatabasePath(DATABASE_FILE_NAME);
    }

    public static int getRequiredDataBaseVersion() {
        return DATABASE_VERSION;
    }
}
