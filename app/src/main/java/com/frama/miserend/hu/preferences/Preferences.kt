package com.frama.miserend.hu.preferences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Balazs on 2018. 03. 04..
 */

class Preferences(context: Context) {

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(
                PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)
    }

    fun getDatabaseLastUpdated(): Long = getLong(DATABASE_LAST_UPDATED, 0)

    fun setDatabaseLastUpdated(time: Long) = putLong(DATABASE_LAST_UPDATED, time)

    fun getSavedDatabaseVersion(): Int = getInt(SAVED_DATABASE_VERSION, 4)

    fun setSavedDatabaseVersion(version: Int) = putInt(SAVED_DATABASE_VERSION, version)

    @SuppressLint("ApplySharedPref")
    private fun putLong(key: String, value: Long) = sharedPreferences.edit().putLong(key, value).commit()

    private fun getLong(key: String, defaultValue: Long): Long = sharedPreferences.getLong(key, defaultValue)

    @SuppressLint("ApplySharedPref")
    private fun putInt(key: String, value: Int) = sharedPreferences.edit().putInt(key, value).commit()

    private fun getInt(key: String, defaultValue: Int): Int = sharedPreferences.getInt(key, defaultValue)

    companion object {

        private const val PREFERENCE_FILE_KEY = "com.frama.miserend.hu.preferences.PREFERENCE_FILE_KEY"

        private const val DATABASE_LAST_UPDATED = "DATABASE_LAST_UPDATED"

        private const val SAVED_DATABASE_VERSION = "SAVED_DATABASE_VERSION"
    }
}
