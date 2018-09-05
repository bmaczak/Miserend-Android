package com.frama.miserend.hu.home.pages.churches.favorites.di;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;

import com.frama.miserend.hu.di.scopes.PerFragment;
import com.frama.miserend.hu.home.pages.churches.favorites.FavoriteChurchesAdapter;
import com.frama.miserend.hu.home.pages.churches.favorites.FavoriteChurchesFragment;
import com.frama.miserend.hu.home.pages.churches.favorites.FavoritesViewModel;
import com.frama.miserend.hu.repository.FavoritesRepository;
import com.frama.miserend.hu.repository.MiserendRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by maczak on 2018. 02. 16..
 */
@Module
public class FavoritesFragmentModule {

    @PerFragment
    @Provides
    Fragment provideFragment(FavoriteChurchesFragment fragment) {
        return fragment;
    }

    @PerFragment
    @Provides
    FavoriteChurchesAdapter provideFavoriteChurchesAdapter(FavoriteChurchesFragment fragment) {
        return new FavoriteChurchesAdapter(fragment);
    }

    @PerFragment
    @Provides
    FavoritesViewModel provideFavoritesViewModel(Fragment fragment, FavoritesViewModel.Factory factory) {
        return ViewModelProviders.of(fragment.getActivity(), factory)
                .get(FavoritesViewModel.class);
    }

    @PerFragment
    @Provides
    FavoritesViewModel.Factory provideFavoritesViewModelFactory(Application application, FavoritesRepository favoritesRepository, MiserendRepository miserendRepository) {
        return new FavoritesViewModel.Factory(application, favoritesRepository,miserendRepository);
    }
}
