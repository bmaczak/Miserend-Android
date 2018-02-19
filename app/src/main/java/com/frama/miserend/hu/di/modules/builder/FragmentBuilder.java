package com.frama.miserend.hu.di.modules.builder;

import com.frama.miserend.hu.di.modules.FavoritesFragmentModule;
import com.frama.miserend.hu.di.modules.FavoritesModule;
import com.frama.miserend.hu.di.modules.LocationModule;
import com.frama.miserend.hu.di.modules.NearChurchesFragmentModule;
import com.frama.miserend.hu.di.scopes.PerFragment;
import com.frama.miserend.hu.home.pages.churches.favorites.FavoriteChurchesFragment;
import com.frama.miserend.hu.home.pages.churches.near.NearChurchesFragment;
import com.frama.miserend.hu.home.pages.masses.MassesFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Balazs on 2018. 02. 18..
 */

@Module
public abstract class FragmentBuilder {

    @PerFragment
    @ContributesAndroidInjector(modules = {NearChurchesFragmentModule.class, LocationModule.class, FavoritesModule.class})
    abstract NearChurchesFragment bindNearChurchesFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = {FavoritesModule.class, FavoritesFragmentModule.class})
    abstract FavoriteChurchesFragment bindFavoriteChurchesFragment();

    @PerFragment
    @ContributesAndroidInjector
    abstract MassesFragment bindMassesFragment();
}
