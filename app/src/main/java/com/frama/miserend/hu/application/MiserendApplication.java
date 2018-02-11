package com.frama.miserend.hu.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.frama.miserend.hu.di.components.AppComponent;

/**
 * Created by Balazs on 2018. 02. 11..
 */

public class MiserendApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        AppComponent.Injector.inject(this);
    }
}
