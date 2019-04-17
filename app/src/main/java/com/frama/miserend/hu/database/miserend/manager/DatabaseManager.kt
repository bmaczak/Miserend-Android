package com.frama.miserend.hu.database.miserend.manager

import android.content.Context

import com.frama.miserend.hu.api.MiserendApi
import com.frama.miserend.hu.database.miserend.dao.ChurchDao
import com.frama.miserend.hu.preferences.Preferences

import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

import io.reactivex.Single

/**
 * Created by Maczi on 2013.05.16..
 */
class DatabaseManager(private val context: Context, private val api: MiserendApi, private val preferences: Preferences, private val churchDao: ChurchDao) {

    fun downloadDatabase(listener: DatabaseDownloaderTask.OnDbDownloadedListener) {
        DatabaseDownloaderTask(context, listener).execute(DATABASE_URL)
    }

    val databaseState: Single<DatabaseState>
        get() = Single.just(if (!dbExists) DatabaseState.NOT_FOUND else DatabaseState.UP_TO_DATE)
                .flatMap { state -> if (state == DatabaseState.UP_TO_DATE) churchDao.getChurchesCount().map { count -> if (count > 0) state else DatabaseState.DATABASE_CORRUPT } else Single.just(state) }
                .map { state -> if (state == DatabaseState.UP_TO_DATE && DatabaseManager.DATABASE_VERSION != preferences.getSavedDatabaseVersion()) DatabaseState.VERSION_INCOMPATIBLE else state }
                .flatMap { state -> if (state == DatabaseState.UP_TO_DATE) checkUpdate() else Single.just(state) }

    val dbExists: Boolean
        get() = databaseFile.exists()

    private val databaseFile: File
        get() = context.getDatabasePath(DATABASE_FILE_NAME)

    private fun checkUpdate(): Single<DatabaseState> {
        return if (Calendar.getInstance().timeInMillis > preferences.getDatabaseLastUpdated() + DB_UPDATE_CHECK_PERIOD_IN_MILLIS) {
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
            val updated = format.format(Date(preferences.getDatabaseLastUpdated()))
            api.updateAvailable(updated)
                    .map { integer -> if (integer == 1) DatabaseState.UPDATE_AVAILABLE else DatabaseState.UP_TO_DATE }
        } else {
            Single.just(DatabaseState.UP_TO_DATE)
        }
    }

    companion object {
        const val DATABASE_FILE_NAME = "miserend.sqlite3"
        const val DATABASE_VERSION = 4

        private const val DATABASE_URL = "http://miserend.hu/fajlok/sqlite/miserend_v$DATABASE_VERSION.sqlite3"
        private const val DB_UPDATE_CHECK_PERIOD_IN_MILLIS = 1000 * 60 * 60 * 24 * 7
    }
}
