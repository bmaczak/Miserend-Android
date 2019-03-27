package com.frama.miserend.hu.di.modules

import android.app.Activity

import com.frama.miserend.hu.di.scopes.PerActivity
import com.frama.miserend.hu.router.Router

import dagger.Module
import dagger.Provides

/**
 * Created by Balazs on 2018. 02. 12..
 */
@Module
class RouterModule {

    @Provides
    @PerActivity
    internal fun provideRouter(activity: Activity): Router {
        return Router(activity)
    }
}
