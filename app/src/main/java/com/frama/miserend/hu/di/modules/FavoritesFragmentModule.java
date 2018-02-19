package com.frama.miserend.hu.di.modules;

import android.support.v4.app.Fragment;

import com.frama.miserend.hu.di.scopes.PerFragment;
import com.frama.miserend.hu.home.pages.churches.favorites.FavoriteChurchesAdapter;
import com.frama.miserend.hu.home.pages.churches.favorites.FavoriteChurchesFragment;

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
}
