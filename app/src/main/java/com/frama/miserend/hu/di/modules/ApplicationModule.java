package com.frama.miserend.hu.di.modules;

import android.app.Application;
import android.content.Context;

import com.frama.miserend.hu.di.components.HomeScreenActivityComponent;
import com.frama.miserend.hu.di.qualifiers.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Balazs on 2018. 02. 11..
 */
@Module(subcomponents = {
        HomeScreenActivityComponent.class})
public class ApplicationModule {


    @Provides
    @Singleton
    @ApplicationContext
    Context provideApplicationContext(Application application) {
        return application.getApplicationContext();
    }
}
