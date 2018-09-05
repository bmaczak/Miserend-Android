package com.frama.miserend.hu.home.di;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;

import com.frama.miserend.hu.database.local.LocalDatabase;
import com.frama.miserend.hu.database.miserend.MiserendDatabase;
import com.frama.miserend.hu.database.miserend.manager.DatabaseManager;
import com.frama.miserend.hu.di.scopes.PerActivity;
import com.frama.miserend.hu.di.scopes.PerFragment;
import com.frama.miserend.hu.home.pages.churches.favorites.FavoriteChurchesFragment;
import com.frama.miserend.hu.home.pages.churches.favorites.di.FavoritesFragmentModule;
import com.frama.miserend.hu.home.pages.churches.near.NearChurchesFragment;
import com.frama.miserend.hu.home.pages.churches.near.di.NearChurchesFragmentModule;
import com.frama.miserend.hu.home.pages.churches.view.ChurchesFragment;
import com.frama.miserend.hu.home.pages.map.di.ChurchesMapFragmentModule;
import com.frama.miserend.hu.home.pages.map.view.ChurchesMapFragment;
import com.frama.miserend.hu.home.pages.masses.di.MassesFragmentModule;
import com.frama.miserend.hu.home.pages.masses.view.MassesFragment;
import com.frama.miserend.hu.home.view.HomeScreenActivity;
import com.frama.miserend.hu.home.viewmodel.HomeViewModel;
import com.frama.miserend.hu.preferences.Preferences;
import com.frama.miserend.hu.repository.MiserendRepository;
import com.frama.miserend.hu.repository.RecentSearchesRepository;
import com.frama.miserend.hu.search.suggestions.viewmodel.SuggestionViewModel;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Balazs on 2018. 02. 11..
 */
@Module
public abstract class HomeScreenModule {

    @PerActivity
    @Provides
    static Activity provideActivity(HomeScreenActivity homeScreenActivity) {
        return homeScreenActivity;
    }

    @PerActivity
    @Provides
    static HomeViewModel provideHomeViewModel(HomeScreenActivity homeScreenActivity, HomeViewModel.Factory factory) {
        return ViewModelProviders.of(homeScreenActivity, factory)
                .get(HomeViewModel.class);
    }

    @PerActivity
    @Provides
    static HomeViewModel.Factory provideHomeViewModelFactory(Application application, DatabaseManager databaseManager, Preferences preferences) {
        return new HomeViewModel.Factory(application, databaseManager, preferences);
    }

    @PerActivity
    @Provides
    static SuggestionViewModel provideSuggestionViewModel(HomeScreenActivity homeScreenActivity, SuggestionViewModel.Factory factory) {
        return ViewModelProviders.of(homeScreenActivity, factory)
                .get(SuggestionViewModel.class);
    }

    @PerActivity
    @Provides
    static SuggestionViewModel.Factory provideSuggestionViewModelFactory(Application application, MiserendRepository miserendRepository, RecentSearchesRepository recentSearchesRepository) {
        return new SuggestionViewModel.Factory(application, miserendRepository, recentSearchesRepository);
    }

    @PerFragment
    @ContributesAndroidInjector()
    abstract ChurchesFragment bindChurchesFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = {NearChurchesFragmentModule.class})
    abstract NearChurchesFragment bindNearChurchesFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = {FavoritesFragmentModule.class})
    abstract FavoriteChurchesFragment bindFavoriteChurchesFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = {MassesFragmentModule.class})
    abstract MassesFragment bindMassesFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = {ChurchesMapFragmentModule.class})
    abstract ChurchesMapFragment bindChurchesMapFragment();
}
