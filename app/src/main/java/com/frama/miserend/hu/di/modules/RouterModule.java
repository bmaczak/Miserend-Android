package com.frama.miserend.hu.di.modules;

import android.app.Activity;

import com.frama.miserend.hu.di.scopes.PerActivity;
import com.frama.miserend.hu.router.Router;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Balazs on 2018. 02. 12..
 */
@Module
public class RouterModule {

    @Provides
    @PerActivity
    Router provideRouter(Activity activity) {
        return new Router(activity);
    }
}
