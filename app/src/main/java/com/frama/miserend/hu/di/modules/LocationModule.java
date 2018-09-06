package com.frama.miserend.hu.di.modules;

import android.app.Activity;

import com.frama.miserend.hu.di.scopes.PerActivity;
import com.frama.miserend.hu.location.LocationPermissionHelper;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Balazs on 2018. 02. 12..
 */
@Module
public class LocationModule {

    @Provides
    @PerActivity
    LocationPermissionHelper provideLocationRetriever(Activity activity) {
        return new LocationPermissionHelper(activity);
    }
}
