package com.frama.miserend.hu.home.pages.masses.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.location.Location;
import android.support.annotation.NonNull;

import com.frama.miserend.hu.database.miserend.MiserendDatabase;
import com.frama.miserend.hu.database.miserend.relations.MassWithChurch;
import com.frama.miserend.hu.home.pages.churches.filter.MassFilter;
import com.frama.miserend.hu.home.pages.masses.model.MassComparator;
import com.frama.miserend.hu.location.LocationError;
import com.frama.miserend.hu.location.LocationRepository;
import com.frama.miserend.hu.repository.MiserendRepository;

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

    private MiserendRepository miserendRepository;
    private LocationRepository locationRepository;

    public MassesViewModel(@NonNull Application application, MiserendRepository miserendRepository, LocationRepository locationRepository) {
        super(application);
        this.miserendRepository = miserendRepository;
        this.locationRepository = locationRepository;
    }

    public LiveData<List<MassWithChurch>> getRecommendedMasses() {
        return Transformations.switchMap(locationRepository.getLocation(), location -> miserendRepository.getRecommendedMasses(location));
    }

    public LiveData<LocationError> getLocationError() {
        return locationRepository.getLocationError();
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final MiserendRepository miserendRepository;
        private final LocationRepository locationRepository;

        public Factory(@NonNull Application mApplication, MiserendRepository miserendRepository, LocationRepository locationRepository) {
            this.mApplication = mApplication;
            this.miserendRepository = miserendRepository;
            this.locationRepository = locationRepository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MassesViewModel(mApplication, miserendRepository, locationRepository);
        }
    }
}
