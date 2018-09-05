package com.frama.miserend.hu.home.pages.map.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.frama.miserend.hu.database.miserend.MiserendDatabase;
import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses;
import com.frama.miserend.hu.repository.FavoritesRepository;
import com.frama.miserend.hu.repository.MiserendRepository;

import java.util.List;

/**
 * Created by Balazs on 2018. 03. 02..
 */

public class ChurchesMapViewModel extends AndroidViewModel {

    private MiserendRepository miserendRepository;
    private FavoritesRepository favoritesRepository;

    public ChurchesMapViewModel(@NonNull Application application, MiserendRepository miserendRepository, FavoritesRepository favoritesRepository) {
        super(application);
        this.miserendRepository = miserendRepository;
        this.favoritesRepository = favoritesRepository;
    }

    public LiveData<List<Church>> getChurcesLiveData() {
        return miserendRepository.getAllChurch();
    }

    public LiveData<ChurchWithMasses> selectChurch(int churchId) {
        return miserendRepository.getChurch(churchId);
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

        public Factory(@NonNull Application mApplication, MiserendRepository miserendRepository, FavoritesRepository favoritesRepository) {
            this.mApplication = mApplication;
            this.miserendRepository = miserendRepository;
            this.favoritesRepository = favoritesRepository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ChurchesMapViewModel(mApplication, miserendRepository, favoritesRepository);
        }
    }
}
