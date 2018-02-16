package com.frama.miserend.hu.di.components;

import android.app.Application;
import android.content.Context;

import com.frama.miserend.hu.application.MiserendApplication;
import com.frama.miserend.hu.database.local.LocalDatabase;
import com.frama.miserend.hu.database.miserend.MiserendDatabase;
import com.frama.miserend.hu.database.miserend.manager.DatabaseManager;
import com.frama.miserend.hu.di.modules.ApplicationModule;
import com.frama.miserend.hu.di.modules.DatabaseModule;
import com.frama.miserend.hu.di.qualifiers.ApplicationContext;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by maczak on 2018. 02. 09..
 */

@Singleton
@Component(modules = {ApplicationModule.class, DatabaseModule.class})
public interface AppComponent {

    void inject(MiserendApplication miserendApplication);

    Application application();

    @ApplicationContext
    Context context();

    MiserendDatabase miserendDatabase();

    LocalDatabase localDatabase();

    DatabaseManager databaseManager();

    final class Injector {
        private static AppComponent appComponent;

        private Injector() {
        }

        public static void inject(MiserendApplication application) {
            appComponent = DaggerAppComponent.builder()
                    .applicationModule(new ApplicationModule(application))
                    .build();
            appComponent.inject(application);
        }

        public static AppComponent getComponent() {
            return appComponent;
        }
    }
}
