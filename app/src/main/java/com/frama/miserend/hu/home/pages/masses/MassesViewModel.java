package com.frama.miserend.hu.home.pages.masses;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.frama.miserend.hu.database.miserend.MiserendDatabase;
import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses;
import com.frama.miserend.hu.database.miserend.relations.MassWithChuch;

/**
 * Created by Balazs on 2018. 02. 10..
 */

public class MassesViewModel extends AndroidViewModel {

    private LiveData<PagedList<MassWithChuch>> churches;
    private MiserendDatabase database;

    public MassesViewModel(@NonNull Application application, MiserendDatabase database) {
        super(application);
        this.database = database;
    }


    public LiveData<PagedList<MassWithChuch>> getRecommendedMasses(double latitude, double longitude) {
        churches = new LivePagedListBuilder<>(
                database.massesDao().getRecommendedMasses(latitude, longitude, 2), 20).build();
        return churches;
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
            return (T) new MassesViewModel(mApplication, database);
        }
    }
}
