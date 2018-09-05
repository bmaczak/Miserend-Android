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
import com.frama.miserend.hu.repository.MiserendRepository;
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

    private final MiserendRepository miserendRepository;

    private LocalDate date;
    private LocalTime fromTime;
    private LocalTime toTime;
    private boolean filterForMasses;
    private boolean allDay;

    public AdvancedSearchViewModel(@NonNull Application application, MiserendRepository miserendRepository) {
        super(application);
        this.miserendRepository = miserendRepository;
        init();
    }

    private void init() {
        date = LocalDate.now().with(DayOfWeek.SUNDAY);
        fromTime = LocalTime.of(7, 0);
        toTime = LocalTime.of(20, 0);
    }

    public LiveData<List<String>> getCities() {
        return miserendRepository.getCities();
    }

    public SearchParams createSearchParams(String churchName, String city) {
        SearchParams searchParams = new SearchParams();
        searchParams.setCity(city);
        searchParams.setChurchName(churchName);
        searchParams.setDate(date);
        searchParams.setFromTime(fromTime);
        searchParams.setToTime(toTime);
        searchParams.setFilterForMasses(filterForMasses);
        searchParams.setAllDay(allDay);
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

    public boolean isFilterForMasses() {
        return filterForMasses;
    }

    public void setFilterForMasses(boolean filterForMasses) {
        this.filterForMasses = filterForMasses;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final MiserendRepository miserendRepository;


        public Factory(@NonNull Application application, MiserendRepository miserendRepository) {
            this.application = application;
            this.miserendRepository = miserendRepository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new AdvancedSearchViewModel(application, miserendRepository);
        }
    }
}
