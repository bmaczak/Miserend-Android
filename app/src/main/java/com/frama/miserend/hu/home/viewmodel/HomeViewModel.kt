package com.frama.miserend.hu.home.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.database.sqlite.SQLiteDatabaseCorruptException

import com.frama.miserend.hu.database.miserend.manager.DatabaseDownloaderTask
import com.frama.miserend.hu.database.miserend.manager.DatabaseManager
import com.frama.miserend.hu.database.miserend.manager.DatabaseState
import com.frama.miserend.hu.location.LocationRepository
import com.frama.miserend.hu.preferences.Preferences

import java.util.Calendar

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Balazs on 2018. 02. 12..
 */

class HomeViewModel(application: Application, private val databaseManager: DatabaseManager, private val preferences: Preferences, private val locationRepository: LocationRepository) : AndroidViewModel(application) {

    private val databaseState: MutableLiveData<DatabaseState>

    private val disposables = CompositeDisposable()

    init {
        databaseState = MutableLiveData()
    }

    fun getDatabaseState(): LiveData<DatabaseState> {
        disposables.add(databaseManager.databaseState
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ databaseState -> this.databaseState.setValue(databaseState) },
                        { throwable ->
                            if (throwable is SQLiteDatabaseCorruptException) {
                                this.databaseState.setValue(DatabaseState.DATABASE_CORRUPT)
                            } else if (databaseManager.dbExists) {
                                this.databaseState.setValue(DatabaseState.UP_TO_DATE)
                            } else {
                                this.databaseState.setValue(DatabaseState.NOT_FOUND)
                            }
                        }))
        return databaseState
    }

    fun downloadDatabase() {
        databaseManager.downloadDatabase(object : DatabaseDownloaderTask.OnDbDownloadedListener {
            override fun onDbDownloadStarted() {
                this@HomeViewModel.databaseState.value = DatabaseState.DOWNLOADING
            }

            override fun onDbDownloadFinished(success: Boolean) {
                if (success) {
                    preferences.setSavedDatabaseVersion(DatabaseManager.DATABASE_VERSION)
                    preferences.setDatabaseLastUpdated(Calendar.getInstance().timeInMillis)
                    this@HomeViewModel.databaseState.setValue(DatabaseState.UP_TO_DATE)
                } else {
                    this@HomeViewModel.databaseState.setValue(DatabaseState.NOT_FOUND)
                }
            }
        })
    }

    fun retryLocation() {
        locationRepository.refreshLocation()
    }

    override fun onCleared() {
        disposables.clear()
    }

    class Factory(private val mApplication: Application, private val databaseManager: DatabaseManager, private val preferences: Preferences, private val locationRepository: LocationRepository) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(mApplication, databaseManager, preferences, locationRepository) as T
        }
    }
}
