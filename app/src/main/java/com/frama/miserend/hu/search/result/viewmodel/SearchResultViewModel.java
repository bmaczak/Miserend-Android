package com.frama.miserend.hu.search.result.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses;
import com.frama.miserend.hu.database.miserend.relations.MassWithChurch;
import com.frama.miserend.hu.repository.FavoritesRepository;
import com.frama.miserend.hu.repository.MiserendRepository;
import com.frama.miserend.hu.search.SearchParams;

import java.util.List;

/**
 * Created by Balazs on 2018. 02. 12..
 */

public class SearchResultViewModel extends AndroidViewModel {

    private final MiserendRepository miserendRepository;
    private final FavoritesRepository favoritesRepository;

    private MutableLiveData<List<ChurchWithMasses>> churches;
    private MutableLiveData<List<MassWithChurch>> masses;
    private SearchParams searchParams;

    public SearchResultViewModel(@NonNull Application application, MiserendRepository miserendRepository, FavoritesRepository favoritesRepository, SearchParams searchParams) {
        super(application);
        this.miserendRepository = miserendRepository;
        this.favoritesRepository = favoritesRepository;
        this.searchParams = searchParams;
        churches = new MutableLiveData<>();
        masses = new MutableLiveData<>();
        searchParams.normalize();
    }

    public LiveData<List<ChurchWithMasses>> getChurchSearchResults() {
        return miserendRepository.getChurches(searchParams);
    }

    public LiveData<List<MassWithChurch>> getMassSearchResults() {
        return miserendRepository.getMasses(searchParams);
    }

    public boolean shouldShowMasses() {
        return searchParams.isFilterForMasses();
    }

    public LiveData<List<Integer>> getFavorites() {
        return favoritesRepository.getFavorites();
    }

    public void toggleFavorite(int churchId) {
        favoritesRepository.toggleFavorite(churchId);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final MiserendRepository miserendRepository;
        private final FavoritesRepository favoritesRepository;
        private SearchParams searchParams;


        public Factory(@NonNull Application application, MiserendRepository miserendRepository, FavoritesRepository favoritesRepository, SearchParams searchParams) {
            this.application = application;
            this.miserendRepository = miserendRepository;
            this.favoritesRepository = favoritesRepository;
            this.searchParams = searchParams;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new SearchResultViewModel(application, miserendRepository, favoritesRepository, searchParams);
        }
    }
}
