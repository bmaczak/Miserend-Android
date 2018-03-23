package com.frama.miserend.hu.application.di;

import android.app.Application;
import android.content.Context;

import com.frama.miserend.hu.di.qualifiers.ApplicationContext;
import com.frama.miserend.hu.preferences.Preferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Balazs on 2018. 02. 11..
 */
@Module
public class ApplicationModule {

    @Provides
    @Singleton
    @ApplicationContext
    Context provideApplicationContext(Application application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    Preferences providePreferences(@ApplicationContext Context context) {
        return new Preferences(context);
    }
}
