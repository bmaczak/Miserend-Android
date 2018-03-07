package com.frama.miserend.hu.di.modules;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;

import com.frama.miserend.hu.api.MiserendApi;
import com.frama.miserend.hu.database.miserend.MiserendDatabase;
import com.frama.miserend.hu.database.miserend.manager.DatabaseManager;
import com.frama.miserend.hu.di.scopes.PerActivity;
import com.frama.miserend.hu.home.HomeScreenActivity;
import com.frama.miserend.hu.home.HomeViewModel;
import com.frama.miserend.hu.preferences.Preferences;
import com.frama.miserend.hu.search.suggestions.SuggestionViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Balazs on 2018. 02. 11..
 */
@Module
public class HomeScreenModule {

    @PerActivity
    @Provides
    Activity provideActivity(HomeScreenActivity homeScreenActivity) {
        return homeScreenActivity;
    }

    @PerActivity
    @Provides
    HomeViewModel provideHomeViewModel(HomeScreenActivity homeScreenActivity, HomeViewModel.Factory factory) {
        return ViewModelProviders.of(homeScreenActivity, factory)
                .get(HomeViewModel.class);
    }

    @PerActivity
    @Provides
    HomeViewModel.Factory provideHomeViewModelFactory(Application application, DatabaseManager databaseManager, Preferences preferences) {
        return new HomeViewModel.Factory(application, databaseManager, preferences);
    }

    @PerActivity
    @Provides
    SuggestionViewModel provideSuggestionViewModel(HomeScreenActivity homeScreenActivity, SuggestionViewModel.Factory factory) {
        return ViewModelProviders.of(homeScreenActivity, factory)
                .get(SuggestionViewModel.class);
    }

    @PerActivity
    @Provides
    SuggestionViewModel.Factory provideSuggestionViewModelFactory(Application application, MiserendDatabase miserendDatabase) {
        return new SuggestionViewModel.Factory(application, miserendDatabase);
    }
}
