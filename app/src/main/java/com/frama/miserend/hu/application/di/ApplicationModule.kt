package com.frama.miserend.hu.application.di

import android.app.Application
import android.content.Context

import com.frama.miserend.hu.di.qualifiers.ApplicationContext
import com.frama.miserend.hu.firebase.Analytics
import com.frama.miserend.hu.preferences.Preferences
import com.google.firebase.analytics.FirebaseAnalytics

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

/**
 * Created by Balazs on 2018. 02. 11..
 */
@Module
class ApplicationModule {

    @Provides
    @Singleton
    @ApplicationContext
    internal fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    internal fun providePreferences(@ApplicationContext context: Context): Preferences {
        return Preferences(context)
    }

    @Provides
    @Singleton
    internal fun provideFirebaseAnalytics(@ApplicationContext context: Context): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(context)
    }

    @Provides
    @Singleton
    internal fun provideAnalytics(firebaseAnalytics: FirebaseAnalytics): Analytics {
        return Analytics(firebaseAnalytics)
    }
}
