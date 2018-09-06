package com.frama.miserend.hu.home.pages.map.viewmodel;

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
import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses;
import com.frama.miserend.hu.location.LocationRepository;
import com.frama.miserend.hu.repository.FavoritesRepository;
import com.frama.miserend.hu.repository.MiserendRepository;

import java.util.List;

/**
 * Created by Balazs on 2018. 03. 02..
 */

public class ChurchesMapViewModel extends AndroidViewModel {

    private MiserendRepository miserendRepository;
    private FavoritesRepository favoritesRepository;
    private LocationRepository locationRepository;

    private MutableLiveData<Integer> selectedChurchId;

    public ChurchesMapViewModel(@NonNull Application application, MiserendRepository miserendRepository, FavoritesRepository favoritesRepository, LocationRepository locationRepository) {
        super(application);
        this.miserendRepository = miserendRepository;
        this.favoritesRepository = favoritesRepository;
        this.locationRepository = locationRepository;
        this.selectedChurchId = new MutableLiveData<>();
    }

    public LiveData<List<Church>> getChurcesLiveData() {
        return miserendRepository.getAllChurch();
    }

    public LiveData<ChurchWithMasses> getSelectedChurch() {
        return Transformations.switchMap(selectedChurchId, churchId -> miserendRepository.getChurch(churchId));
    }

    public void selectChurch(int churchId) {
        selectedChurchId.setValue(churchId);
    }

    public LiveData<List<Integer>> getFavorites() {
        return favoritesRepository.getFavorites();
    }

    public LiveData<Location> getLocation() {
        return locationRepository.getLocation();
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
            return (T) new ChurchesMapViewModel(mApplication, miserendRepository, favoritesRepository, locationRepository);
        }
    }
}
