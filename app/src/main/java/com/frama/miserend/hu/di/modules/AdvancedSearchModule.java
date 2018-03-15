package com.frama.miserend.hu.di.modules;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;

import com.frama.miserend.hu.database.miserend.MiserendDatabase;
import com.frama.miserend.hu.database.miserend.manager.DatabaseManager;
import com.frama.miserend.hu.di.scopes.PerActivity;
import com.frama.miserend.hu.home.HomeScreenActivity;
import com.frama.miserend.hu.home.HomeViewModel;
import com.frama.miserend.hu.preferences.Preferences;
import com.frama.miserend.hu.search.advanced.AdvancedSearchActivity;
import com.frama.miserend.hu.search.suggestions.SuggestionViewModel;

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
}
