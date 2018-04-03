package com.frama.miserend.hu.search.advanced.di;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;

import com.frama.miserend.hu.churchdetails.view.ChurchDetailsActivity;
import com.frama.miserend.hu.churchdetails.viewmodel.ChurchDetailsViewModel;
import com.frama.miserend.hu.database.local.LocalDatabase;
import com.frama.miserend.hu.database.miserend.MiserendDatabase;
import com.frama.miserend.hu.di.scopes.PerActivity;
import com.frama.miserend.hu.search.advanced.AdvancedSearchActivity;
import com.frama.miserend.hu.search.advanced.AdvancedSearchViewModel;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Balazs on 2018. 02. 11..
 */
@Module
public class AdvancedSearchModule {

    @PerActivity
    @Provides
    Activity provideActivity(AdvancedSearchActivity homeScreenActivity) {
        return homeScreenActivity;
    }

    @PerActivity
    @Provides
    static AdvancedSearchViewModel provideAdvancedSearchViewModel(AdvancedSearchActivity advancedSearchActivity, AdvancedSearchViewModel.Factory factory) {
        return ViewModelProviders.of(advancedSearchActivity, factory)
                .get(AdvancedSearchViewModel.class);
    }

    @PerActivity
    @Provides
    static AdvancedSearchViewModel.Factory provideAdvancedSearchViewModelFactory(Application applicatio, LocalDatabase localDatabase, MiserendDatabase miserendDatabase) {
        return new AdvancedSearchViewModel.Factory(applicatio, miserendDatabase, localDatabase);
    }
}