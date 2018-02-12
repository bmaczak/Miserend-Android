package com.frama.miserend.hu.churchlist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.frama.miserend.hu.database.MiserendDatabase;
import com.frama.miserend.hu.database.relations.ChurchWithMasses;

/**
 * Created by Balazs on 2018. 02. 10..
 */

public class NearChurchesViewModel extends AndroidViewModel {

    private LiveData<PagedList<ChurchWithMasses>> churches;
    private MiserendDatabase database;

    public NearChurchesViewModel(@NonNull Application application, MiserendDatabase database) {
        super(application);
        this.database = database;
    }


    public LiveData<PagedList<ChurchWithMasses>> getNearestChurches(double latitude, double longitude) {
        churches = new LivePagedListBuilder<>(
                database.churchWithMassesDao().getNearChurches(latitude, longitude), 20).build();
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
            return (T) new NearChurchesViewModel(mApplication, database);
        }
    }
}
