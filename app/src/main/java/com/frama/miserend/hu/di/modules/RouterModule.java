package com.frama.miserend.hu.di.modules;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.frama.miserend.hu.di.scopes.PerActivity;
import com.frama.miserend.hu.di.scopes.PerFragment;
import com.frama.miserend.hu.location.LocationRetriever;
import com.frama.miserend.hu.router.Router;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Balazs on 2018. 02. 12..
 */
@Module
public class RouterModule {

    private Activity activity;

    public RouterModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Router provideRouter() {
        return new Router(activity);
    }
}
