package com.frama.miserend.hu.di.modules;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;

import com.frama.miserend.hu.database.local.LocalDatabase;
import com.frama.miserend.hu.database.miserend.MiserendDatabase;
import com.frama.miserend.hu.di.scopes.PerFragment;
import com.frama.miserend.hu.home.pages.churches.ChurchListFragment;
import com.frama.miserend.hu.home.pages.churches.favorites.FavoriteChurchesAdapter;
import com.frama.miserend.hu.home.pages.churches.favorites.FavoritesViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by maczak on 2018. 02. 16..
 */
@Module
public class FavoritesModule {

    private final ChurchListFragment fragment;

    public FavoritesModule(ChurchListFragment fragment) {
        this.fragment = fragment;
    }

    @PerFragment
    @Provides
    FavoritesViewModel provideHomeViewModel(FavoritesViewModel.Factory factory) {
        return ViewModelProviders.of(fragment.getActivity(), factory)
                .get(FavoritesViewModel.class);
    }

    @PerFragment
    @Provides
    FavoritesViewModel.Factory provideFavoritesViewModelFactory(Application application, LocalDatabase localDatabase, MiserendDatabase miserendDatabase) {
        return new FavoritesViewModel.Factory(application, localDatabase, miserendDatabase);
    }

    @PerFragment
    @Provides
    FavoriteChurchesAdapter provideFavoriteChurchesAdapter() {
        return new FavoriteChurchesAdapter(fragment);
    }
}
