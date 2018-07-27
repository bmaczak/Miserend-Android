package com.frama.miserend.hu.di.modules;

import android.app.Activity;

import com.frama.miserend.hu.di.scopes.PerActivity;
import com.frama.miserend.hu.location.LocationManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Balazs on 2018. 02. 12..
 */
@Module
public class LocationModule {

    @Provides
    @PerActivity
    LocationManager provideLocationRetriever(Activity activity) {
        return new LocationManager(activity);
    }
}
