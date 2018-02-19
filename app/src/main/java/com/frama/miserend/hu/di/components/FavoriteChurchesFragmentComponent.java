package com.frama.miserend.hu.di.components;

import com.frama.miserend.hu.di.modules.FavoritesFragmentModule;
import com.frama.miserend.hu.di.modules.FavoritesModule;
import com.frama.miserend.hu.di.scopes.PerFragment;
import com.frama.miserend.hu.home.pages.churches.favorites.FavoriteChurchesFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by Balazs on 2018. 02. 11..
 */
@PerFragment
@Subcomponent(modules = {FavoritesModule.class, FavoritesFragmentModule.class})
public interface FavoriteChurchesFragmentComponent extends AndroidInjector<FavoriteChurchesFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<FavoriteChurchesFragment> {
        public abstract FavoriteChurchesFragmentComponent.Builder favoritesModule(FavoritesModule favoritesModule);
    }
}