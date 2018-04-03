package com.frama.miserend.hu.search.result.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.frama.miserend.hu.database.miserend.MiserendDatabase;
import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses;
import com.frama.miserend.hu.home.pages.churches.filter.MassFilter;
import com.frama.miserend.hu.search.SearchParams;
import com.frama.miserend.hu.utils.Validation;

import java.util.Calendar;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Balazs on 2018. 02. 12..
 */

public class SearchResultViewModel extends AndroidViewModel {

    private final MiserendDatabase miserendDatabase;
    private MutableLiveData<List<ChurchWithMasses>> churches;

    public SearchResultViewModel(@NonNull Application application, MiserendDatabase miserendDatabase) {
        super(application);
        this.miserendDatabase = miserendDatabase;
        churches = new MutableLiveData<>();
    }

    public LiveData<List<ChurchWithMasses>> getChurchSearchResults(SearchParams searchParams) {
        normalize(searchParams);
        selectChurchListFlowable(searchParams)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(churchWithMasses -> {
                    for (int i = churchWithMasses.size() - 1; i >= 0; --i) {
                        if (MassFilter.filterForDay(churchWithMasses.get(i).getMasses(), Calendar.getInstance()).isEmpty()) {
                            churchWithMasses.remove(i);
                        }
                    }
                    return churchWithMasses;
                })
                .subscribe(churchWithMasses -> churches.setValue(churchWithMasses));
        return churches;
    }

    private Flowable<List<ChurchWithMasses>> selectChurchListFlowable(SearchParams searchParams) {
        if (Validation.notEmpty(searchParams.getSearchTerm())) {
            return miserendDatabase.churchWithMassesDao()
                    .getByName(searchParams.getSearchTerm());
        } else {
            return miserendDatabase.churchWithMassesDao()
                    .getBySearch(searchParams.getChurchName(), searchParams.getCity());
        }
    }

    private void normalize(SearchParams searchParams) {
        if (searchParams.getSearchTerm() == null) {
            searchParams.setSearchTerm("");
        }
        if (searchParams.getChurchName() == null) {
            searchParams.setChurchName("");
        }
        if (searchParams.getCity() == null) {
            searchParams.setCity("");
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