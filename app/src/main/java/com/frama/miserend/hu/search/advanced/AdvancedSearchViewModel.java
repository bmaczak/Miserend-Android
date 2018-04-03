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
import com.frama.miserend.hu.search.SearchParams;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Balazs on 2018. 02. 12..
 */

public class AdvancedSearchViewModel extends AndroidViewModel {

    private final MiserendDatabase miserendDatabase;
    private final LocalDatabase localDatabase;

    private MutableLiveData<List<String>> cities;

    private LocalDate date;
    private LocalTime fromTime;
    private LocalTime toTime;

    public AdvancedSearchViewModel(@NonNull Application application, MiserendDatabase miserendDatabase, LocalDatabase localDatabase) {
        super(application);
        this.miserendDatabase = miserendDatabase;
        this.localDatabase = localDatabase;
        this.cities = new MutableLiveData<>();
        init();
    }

    private void init() {
        date = LocalDate.now().with(DayOfWeek.SUNDAY);
        fromTime = LocalTime.of(7, 0);
        toTime = LocalTime.of(20, 0);
    }

    public LiveData<List<String>> getCities() {
        miserendDatabase.churchDao().getAllCities()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(strings -> cities.setValue(strings));
        return cities;
    }

    public SearchParams createSearchParams(String churchName, String city) {
        SearchParams searchParams = new SearchParams();
        searchParams.setCity(city);
        searchParams.setChurchName(churchName);
        searchParams.setDate(date);
        searchParams.setFromTime(fromTime);
        searchParams.setToTime(toTime);
        return searchParams;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getFromTime() {
        return fromTime;
    }

    public void setFromTime(LocalTime fromTime) {
        this.fromTime = fromTime;
    }

    public LocalTime getToTime() {
        return toTime;
    }

    public void setToTime(LocalTime toTime) {
        this.toTime = toTime;
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
