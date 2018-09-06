package com.frama.miserend.hu.home.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.frama.miserend.hu.database.miserend.manager.DatabaseDownloaderTask;
import com.frama.miserend.hu.database.miserend.manager.DatabaseManager;
import com.frama.miserend.hu.database.miserend.manager.DatabaseState;
import com.frama.miserend.hu.location.LocationRepository;
import com.frama.miserend.hu.preferences.Preferences;

import java.util.Calendar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Balazs on 2018. 02. 12..
 */

public class HomeViewModel extends AndroidViewModel {

    private DatabaseManager databaseManager;

    private MutableLiveData<DatabaseState> databaseState;
    private Preferences preferences;
    private LocationRepository locationRepository;

    public HomeViewModel(@NonNull Application application, DatabaseManager databaseManager, Preferences preferences, LocationRepository locationRepository) {
        super(application);
        this.databaseManager = databaseManager;
        this.preferences = preferences;
        this.locationRepository = locationRepository;
        databaseState = new MutableLiveData<>();
    }

    public LiveData<DatabaseState> getDatabaseState() {
        databaseManager.getDatabaseState()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(databaseState -> this.databaseState.setValue(databaseState),
                        throwable -> {
                            if (databaseManager.isDbExist()) {
                                this.databaseState.setValue(DatabaseState.UP_TO_DATE);
                            } else {
                                this.databaseState.setValue(DatabaseState.NOT_FOUND);
                            }
                        });
        return databaseState;
    }

    public void downloadDatabase() {
        databaseManager.downloadDatabase(new DatabaseDownloaderTask.OnDbDownloadedListener() {
            @Override
            public void onDbDownloadStarted() {
                HomeViewModel.this.databaseState.setValue(DatabaseState.DOWNLOADING);
            }

            @Override
            public void onDbDownloadFinished(boolean success) {
                if (success) {
                    preferences.setDatabaseLastUpdated(Calendar.getInstance().getTimeInMillis());
                    HomeViewModel.this.databaseState.setValue(DatabaseState.UP_TO_DATE);
                } else {
                    HomeViewModel.this.databaseState.setValue(DatabaseState.NOT_FOUND);
                }
            }
        });
    }

    public void retryLocation() {
        locationRepository.refreshLocation();
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;
        private final DatabaseManager databaseManager;
        private final Preferences preferences;
        private final LocationRepository locationRepository;

        public Factory(@NonNull Application application, DatabaseManager databaseManager, Preferences preferences, LocationRepository locationRepository) {
            this.mApplication = application;
            this.databaseManager = databaseManager;
            this.preferences = preferences;
            this.locationRepository = locationRepository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new HomeViewModel(mApplication, databaseManager, preferences, locationRepository);
        }
    }
}
