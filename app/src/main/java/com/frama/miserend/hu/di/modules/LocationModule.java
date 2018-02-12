package com.frama.miserend.hu.di.modules;

import android.support.v4.app.Fragment;

import com.frama.miserend.hu.di.scopes.PerFragment;
import com.frama.miserend.hu.location.LocationRetriever;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Balazs on 2018. 02. 12..
 */
@Module
public class LocationModule {

    private Fragment fragment;
    private LocationRetriever.LocationResultListener locationResultListener;

    public LocationModule(Fragment fragment, LocationRetriever.LocationResultListener locationResultListener) {
        this.fragment = fragment;
        this.locationResultListener = locationResultListener;
    }

    @Provides
    @PerFragment
    LocationRetriever provideLocationRetriever() {
        return new LocationRetriever(fragment, locationResultListener);
    }
}
