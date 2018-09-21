package com.frama.miserend.hu.application.di;

import android.app.Application;
import android.content.Context;

import com.frama.miserend.hu.di.qualifiers.ApplicationContext;
import com.frama.miserend.hu.firebase.Analytics;
import com.frama.miserend.hu.preferences.Preferences;
import com.google.firebase.analytics.FirebaseAnalytics;

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

    @Provides
    @Singleton
    FirebaseAnalytics provideFirebaseAnalytics(@ApplicationContext Context context) {
        return FirebaseAnalytics.getInstance(context);
    }

    @Provides
    @Singleton
    Analytics provideAnalytics(FirebaseAnalytics firebaseAnalytics) {
        return new Analytics(firebaseAnalytics);
    }
}
