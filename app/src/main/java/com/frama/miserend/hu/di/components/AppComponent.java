package com.frama.miserend.hu.di.components;

import android.app.Application;

import com.frama.miserend.hu.application.MiserendApplication;
import com.frama.miserend.hu.di.modules.builder.ActivityBuilder;
import com.frama.miserend.hu.di.modules.ApplicationModule;
import com.frama.miserend.hu.di.modules.DatabaseModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by maczak on 2018. 02. 09..
 */

@Singleton
@Component(modules = {AndroidInjectionModule.class, ApplicationModule.class, DatabaseModule.class, ActivityBuilder.class})
public interface AppComponent {

    void inject(MiserendApplication application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
