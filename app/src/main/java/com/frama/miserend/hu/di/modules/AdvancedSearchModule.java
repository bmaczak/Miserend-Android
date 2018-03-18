package com.frama.miserend.hu.di.modules;

import android.app.Activity;

import com.frama.miserend.hu.di.scopes.PerActivity;
import com.frama.miserend.hu.search.advanced.AdvancedSearchActivity;

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
