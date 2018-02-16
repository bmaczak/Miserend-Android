package com.frama.miserend.hu.home;

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

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Balazs on 2018. 02. 12..
 */

public class HomeViewModel extends AndroidViewModel {

    private DatabaseManager databaseManager;

    private MutableLiveData<DatabaseState> databaseState;

    public HomeViewModel(@NonNull Application application, DatabaseManager databaseManager) {
        super(application);
        this.databaseManager = databaseManager;
        databaseState = new MutableLiveData<>();
    }

    public LiveData<DatabaseState> getDatabaseState() {
        Flowable.just(databaseManager.isDbExist() ? DatabaseState.UP_TO_DATE : DatabaseState.NOT_FOUND)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(databaseState -> this.databaseState.setValue(databaseState));
        return databaseState;
    }

    public void downloadDatabase() {
        databaseManager.downloadDatabase(new DatabaseDownloaderTask.OnDbDownloadedListener() {
            @Override
            public void onDbDownloadStarted() {

            }

            @Override
            public void onDbDownloadFinished(boolean success) {
                HomeViewModel.this.databaseState.setValue(DatabaseState.UP_TO_DATE);
            }
        });
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final DatabaseManager databaseManager;

        public Factory(@NonNull Application application, DatabaseManager databaseManager) {
            this.mApplication = application;
            this.databaseManager = databaseManager;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new HomeViewModel(mApplication, databaseManager);
        }
    }
}
