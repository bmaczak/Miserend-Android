package com.frama.miserend.hu.di.modules;

import android.app.Application;
import android.content.Context;

import com.frama.miserend.hu.application.MiserendApplication;
import com.frama.miserend.hu.di.qualifiers.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Balazs on 2018. 02. 11..
 */
@Module
public class ApplicationModule {

    private final MiserendApplication application;

    public ApplicationModule(MiserendApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @ApplicationContext
    Context provideApplicationContext() {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }
}
