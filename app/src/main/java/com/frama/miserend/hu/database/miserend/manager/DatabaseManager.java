package com.frama.miserend.hu.database.miserend.manager;

import android.content.Context;

import com.frama.miserend.hu.api.MiserendApi;
import com.frama.miserend.hu.preferences.Preferences;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.reactivex.Single;

/**
 * Created by Maczi on 2013.05.16..
 */
public class DatabaseManager {

    public static String DATABASE_FILE_NAME = "miserend.sqlite3";

    private static String TAG = "DatabaseManager";
    private static int DATABASE_VERSION = 4;
    private static final String DATABASE_URL = "http://miserend.hu/fajlok/sqlite/miserend_v" + DATABASE_VERSION + ".sqlite3";
    private static final int DB_UPDATE_CHECK_PERIOD_IN_MILLIS = 1000 * 60 * 60 * 24 * 7;

    private Context context;
    private MiserendApi api;
    private Preferences preferences;

    public DatabaseManager(Context context, MiserendApi api, Preferences preferences) {
        this.context = context;
        this.api = api;
        this.preferences = preferences;
    }


    public boolean isDbExist() {
        return getDatabaseFile().exists();
    }

    public File getDatabaseFile() {
        return context.getDatabasePath(DATABASE_FILE_NAME);
    }

    public int getRequiredDataBaseVersion() {
        return DATABASE_VERSION;
    }

    public Single<DatabaseState> getDatabaseState() {
        if (!isDbExist()) {
            return Single.just(DatabaseState.NOT_FOUND);
        } else if (Calendar.getInstance().getTimeInMillis() > preferences.getDatabaseLastUpdated() + DB_UPDATE_CHECK_PERIOD_IN_MILLIS) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT);
            String updated = format.format(new Date(preferences.getDatabaseLastUpdated()));
            return api.updateAvailable(updated)
                    .map(integer -> integer == 1 ? DatabaseState.UPDATE_AVAILABLE : DatabaseState.UP_TO_DATE);
        } else {
            return Single.just(DatabaseState.UP_TO_DATE);
        }
    }

    public void downloadDatabase(DatabaseDownloaderTask.OnDbDownloadedListener listener) {
        new DatabaseDownloaderTask(context, listener).execute(DATABASE_URL);
    }
}
