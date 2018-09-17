package com.frama.miserend.hu.home.pages.churches.near;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.paging.PagedList;
import android.location.Location;
import android.support.annotation.NonNull;

import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses;
import com.frama.miserend.hu.location.LocationError;
import com.frama.miserend.hu.location.LocationRepository;
import com.frama.miserend.hu.repository.FavoritesRepository;
import com.frama.miserend.hu.repository.MiserendRepository;

import java.util.List;

/**
 * Created by Balazs on 2018. 02. 10..
 */

public class NearChurchesViewModel extends AndroidViewModel {

    private LiveData<PagedList<ChurchWithMasses>> churches;
    private MutableLiveData<Location> locationLiveData;

    private MiserendRepository miserendRepository;
    private FavoritesRepository favoritesRepository;
    private LocationRepository locationRepository;

    public NearChurchesViewModel(@NonNull Application application, MiserendRepository miserendRepository, FavoritesRepository favoritesRepository, LocationRepository locationRepository) {
        super(application);
        this.miserendRepository = miserendRepository;
        this.favoritesRepository = favoritesRepository;
        this.locationRepository = locationRepository;
        locationLiveData = new MutableLiveData<>();
        churches = Transformations.switchMap(locationRepository.getLocation(),
                location -> {
                    locationLiveData.setValue(location);
                    return miserendRepository.getNearChurches(location.getLatitude(), location.getLongitude());
                });
    }

    public LiveData<PagedList<ChurchWithMasses>> getNearestChurches() {
        return churches;
    }

    public LiveData<Location> getLocation() {
        return locationLiveData;
    }

    public MutableLiveData<LocationError> getLocationError() {
        return locationRepository.getLocationError();
    }

    public LiveData<List<Integer>> getFavorites() {
        return favoritesRepository.getFavorites();
    }

    public void toggleFavorite(int churchId) {
        favoritesRepository.toggleFavorite(churchId);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final MiserendRepository miserendRepository;
        private final FavoritesRepository favoritesRepository;
        private final LocationRepository locationRepository;

        public Factory(@NonNull Application mApplication, MiserendRepository miserendRepository, FavoritesRepository favoritesRepository, LocationRepository locationRepository) {
            this.mApplication = mApplication;
            this.miserendRepository = miserendRepository;
            this.favoritesRepository = favoritesRepository;
            this.locationRepository = locationRepository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new NearChurchesViewModel(mApplication, miserendRepository, favoritesRepository, locationRepository);
        }
    }
}
