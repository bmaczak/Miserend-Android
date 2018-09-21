package com.frama.miserend.hu.database.miserend.manager;

/**
 * Created by Balazs on 2018. 02. 12..
 */

public enum DatabaseState {
    NOT_FOUND, VERSION_MISMATCH, UPDATE_AVAILABLE, VERSION_INCOMPATIBLE, UP_TO_DATE, DOWNLOADING
}
