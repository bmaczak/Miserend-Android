package com.frama.miserend.hu.di.modules;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;

import com.frama.miserend.hu.database.manager.DatabaseManager;
import com.frama.miserend.hu.di.scopes.PerActivity;
import com.frama.miserend.hu.home.HomeScreenActivity;
import com.frama.miserend.hu.home.HomeViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Balazs on 2018. 02. 11..
 */
@Module
public class HomeScreenModule {

    private HomeScreenActivity activity;

    public HomeScreenModule(HomeScreenActivity activity) {
        this.activity = activity;
    }

    @PerActivity
    @Provides
    HomeViewModel provideHomeViewModel(HomeViewModel.Factory factory) {
        return ViewModelProviders.of(activity, factory)
                .get(HomeViewModel.class);
    }

    @PerActivity
    @Provides
    HomeViewModel.Factory provideHomeViewModelFactory(Application application, DatabaseManager databaseManager) {
        return new HomeViewModel.Factory(application, databaseManager);
    }

}