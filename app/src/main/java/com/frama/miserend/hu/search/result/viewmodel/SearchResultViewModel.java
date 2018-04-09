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
import com.frama.miserend.hu.database.miserend.relations.MassWithChurch;
import com.frama.miserend.hu.home.pages.churches.filter.MassFilter;
import com.frama.miserend.hu.search.SearchParams;
import com.frama.miserend.hu.utils.Validation;

import org.threeten.bp.LocalDate;

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
    private SearchParams searchParams;

    public SearchResultViewModel(@NonNull Application application, MiserendDatabase miserendDatabase, SearchParams searchParams) {
        super(application);
        this.miserendDatabase = miserendDatabase;
        this.searchParams = searchParams;
        churches = new MutableLiveData<>();
        searchParams.normalize();
    }

    public LiveData<List<ChurchWithMasses>> getChurchSearchResults() {
        selectChurchListFlowable(searchParams)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
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

    public LiveData<List<MassWithChurch>> getMassSearchResults() {
        return miserendDatabase.massesDao().getMassesBySearch(searchParams.getChurchName(), searchParams.getCity(), searchParams.getDate().getDayOfWeek().getValue());
    }

    public boolean shouldShowMasses() {
        return searchParams.isFilterForMasses();
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final MiserendDatabase miserendDatabase;
        private SearchParams searchParams;


        public Factory(@NonNull Application application, MiserendDatabase miserendDatabase, SearchParams searchParams) {
            this.application = application;
            this.miserendDatabase = miserendDatabase;
            this.searchParams = searchParams;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new SearchResultViewModel(application, miserendDatabase, searchParams);
        }
    }
}
