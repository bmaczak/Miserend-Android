package com.frama.miserend.hu.home.pages.masses.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.location.Location;
import android.support.annotation.NonNull;

import com.frama.miserend.hu.database.miserend.MiserendDatabase;
import com.frama.miserend.hu.database.miserend.relations.MassWithChurch;
import com.frama.miserend.hu.home.pages.churches.filter.MassFilter;
import com.frama.miserend.hu.home.pages.masses.model.MassComparator;
import com.frama.miserend.hu.utils.DateUtils;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Balazs on 2018. 02. 10..
 */

public class MassesViewModel extends AndroidViewModel {

    private MutableLiveData<List<MassWithChurch>> masses;
    private MiserendDatabase database;

    public MassesViewModel(@NonNull Application application, MiserendDatabase database) {
        super(application);
        this.database = database;
        this.masses = new MutableLiveData<>();
    }


    public LiveData<List<MassWithChurch>> getRecommendedMasses(Location currentLocation) {
        LocalDate today = LocalDate.now();
        int dayOfWeek = today.getDayOfWeek().getValue();
        database.massesDao().getMassesInRadius(currentLocation.getLatitude(), currentLocation.getLongitude(), dayOfWeek)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(massWithChuches -> {
                    List<MassWithChurch> masses = new ArrayList<>();
                    for (MassWithChurch massWithChurch : massWithChuches) {
                        if (MassFilter.isMassOnDay(massWithChurch.getMass(), LocalDate.now())) {
                            masses.add(massWithChurch);
                        }
                    }
                    return masses;
                })
                .map(massWithChurches -> {
                    Collections.sort(massWithChurches, new MassComparator(currentLocation));
                    return massWithChurches;
                })
                .subscribe(massWithChurches -> masses.setValue(massWithChurches));
        return masses;
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
