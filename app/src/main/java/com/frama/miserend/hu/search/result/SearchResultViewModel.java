package com.frama.miserend.hu.search.result;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.frama.miserend.hu.database.local.LocalDatabase;
import com.frama.miserend.hu.database.local.entities.Favorite;
import com.frama.miserend.hu.database.miserend.MiserendDatabase;
import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses;
import com.frama.miserend.hu.search.SearchParams;
import com.frama.miserend.hu.utils.Validation;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Balazs on 2018. 02. 12..
 */

public class SearchResultViewModel extends AndroidViewModel {

    private final MiserendDatabase miserendDatabase;

    public SearchResultViewModel(@NonNull Application application, MiserendDatabase miserendDatabase) {
        super(application);
        this.miserendDatabase = miserendDatabase;
    }

    public LiveData<List<ChurchWithMasses>> getChurchSearchResults(SearchParams searchParams) {
        normalize(searchParams);
        if (Validation.notEmpty(searchParams.getCity())) {
            return miserendDatabase.churchWithMassesDao().getBySearch(searchParams.getSearchTerm(), searchParams.getCity());
        } else {
            return miserendDatabase.churchWithMassesDao().getBySearch(searchParams.getSearchTerm());
        }
    }

    private void normalize(SearchParams searchParams) {
        if (searchParams.getSearchTerm() == null) {
            searchParams.setSearchTerm("");
        }
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final MiserendDatabase miserendDatabase;


        public Factory(@NonNull Application application, MiserendDatabase miserendDatabase) {
            this.application = application;
            this.miserendDatabase = miserendDatabase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new SearchResultViewModel(application, miserendDatabase);
        }
    }
}
