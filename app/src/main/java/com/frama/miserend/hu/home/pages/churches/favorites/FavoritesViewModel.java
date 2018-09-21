package com.frama.miserend.hu.home.pages.churches.favorites;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses;
import com.frama.miserend.hu.repository.FavoritesRepository;
import com.frama.miserend.hu.repository.MiserendRepository;

import java.util.List;

/**
 * Created by Balazs on 2018. 02. 12..
 */

public class FavoritesViewModel extends AndroidViewModel {

    private FavoritesRepository favoritesRepository;
    private MiserendRepository miserendRepository;

    public FavoritesViewModel(@NonNull Application application, FavoritesRepository favoritesRepository, MiserendRepository miserendRepository) {
        super(application);
        this.favoritesRepository = favoritesRepository;
        this.miserendRepository = miserendRepository;
    }

    public LiveData<List<ChurchWithMasses>> getFavoriteChurches() {
        return Transformations.switchMap(favoritesRepository.getFavorites(), favorites -> miserendRepository.getChurches(favorites));
    }

    public void toggleFavorite(int churchId) {
        favoritesRepository.toggleFavorite(churchId);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final FavoritesRepository favoritesRepository;
        private final MiserendRepository miserendRepository;

        public Factory(@NonNull Application application, FavoritesRepository favoritesRepository, MiserendRepository miserendRepository) {
            this.application = application;
            this.favoritesRepository = favoritesRepository;
            this.miserendRepository = miserendRepository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new FavoritesViewModel(application, favoritesRepository, miserendRepository);
        }
    }
}
