package com.frama.miserend.hu.search.advanced;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.frama.miserend.hu.database.local.LocalDatabase;
import com.frama.miserend.hu.database.miserend.MiserendDatabase;
import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses;
import com.frama.miserend.hu.search.SearchParams;
import com.frama.miserend.hu.utils.Validation;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Balazs on 2018. 02. 12..
 */

public class AdvancedSearchViewModel extends AndroidViewModel {

    private final MiserendDatabase miserendDatabase;
    private final LocalDatabase localDatabase;

    private MutableLiveData<List<String>> cities;

    public AdvancedSearchViewModel(@NonNull Application application, MiserendDatabase miserendDatabase, LocalDatabase localDatabase) {
        super(application);
        this.miserendDatabase = miserendDatabase;
        this.localDatabase = localDatabase;
        this.cities = new MutableLiveData<>();
    }

    public LiveData<List<String>> getCities(String searchTerm) {
        if (searchTerm.length() > 2) {
            miserendDatabase.churchDao().getCities(searchTerm)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(strings -> cities.setValue(strings));
        }
        return cities;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final MiserendDatabase miserendDatabase;
        private final LocalDatabase localDatabase;


        public Factory(@NonNull Application application, MiserendDatabase miserendDatabase, LocalDatabase localDatabase) {
            this.application = application;
            this.miserendDatabase = miserendDatabase;
            this.localDatabase = localDatabase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new AdvancedSearchViewModel(application, miserendDatabase, localDatabase);
        }
    }
}
