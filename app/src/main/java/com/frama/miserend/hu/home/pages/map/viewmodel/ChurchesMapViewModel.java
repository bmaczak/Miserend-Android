package com.frama.miserend.hu.home.pages.map.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.frama.miserend.hu.database.miserend.MiserendDatabase;
import com.frama.miserend.hu.database.miserend.entities.Church;

import java.util.List;

/**
 * Created by Balazs on 2018. 03. 02..
 */

public class ChurchesMapViewModel extends AndroidViewModel {

    private MiserendDatabase miserendDatabase;

    private LiveData<List<Church>> churcesLiveData;

    public ChurchesMapViewModel(@NonNull Application application, MiserendDatabase miserendDatabase) {
        super(application);
        this.miserendDatabase = miserendDatabase;
    }

    public LiveData<List<Church>> getChurcesLiveData() {
        if (churcesLiveData == null) {
            churcesLiveData = miserendDatabase.churchDao().getAll();
        }
        return churcesLiveData;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final MiserendDatabase database;

        public Factory(@NonNull Application application, MiserendDatabase database) {
            mApplication = application;
            this.database = database;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ChurchesMapViewModel(mApplication, database);
        }
    }
}
