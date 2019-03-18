package com.frama.miserend.hu.database.miserend.manager

/**
 * Created by Balazs on 2018. 02. 12..
 */

enum class DatabaseState {
    NOT_FOUND, DATABASE_CORRUPT, UPDATE_AVAILABLE, VERSION_INCOMPATIBLE, UP_TO_DATE, DOWNLOADING
}
