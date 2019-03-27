package com.frama.miserend.hu.application

import android.app.Activity
import android.app.Application

import com.facebook.drawee.backends.pipeline.Fresco
import com.frama.miserend.hu.di.components.DaggerAppComponent
import com.jakewharton.threetenabp.AndroidThreeTen

import javax.inject.Inject

import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector

/**
 * Created by Balazs on 2018. 02. 11..
 */

class MiserendApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        AndroidThreeTen.init(this)
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> = activityInjector
}
