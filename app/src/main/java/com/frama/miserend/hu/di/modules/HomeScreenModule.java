package com.frama.miserend.hu.di.modules;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;

import com.frama.miserend.hu.database.miserend.MiserendDatabase;
import com.frama.miserend.hu.database.miserend.manager.DatabaseManager;
import com.frama.miserend.hu.di.components.FavoriteChurchesFragmentComponent;
import com.frama.miserend.hu.di.components.NearChurchesFragmentComponent;
import com.frama.miserend.hu.di.scopes.PerActivity;
import com.frama.miserend.hu.home.HomeScreenActivity;
import com.frama.miserend.hu.home.HomeViewModel;
import com.frama.miserend.hu.search.suggestions.SuggestionViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Balazs on 2018. 02. 11..
 */
@Module(subcomponents = {
        NearChurchesFragmentComponent.class, FavoriteChurchesFragmentComponent.class})
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
    HomeViewModel.Factory provideHomeViewModelFactory(Application application, DatabaseManager databaseManager) {
        return new HomeViewModel.Factory(application, databaseManager);
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
