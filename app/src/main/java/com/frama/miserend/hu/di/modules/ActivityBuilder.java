package com.frama.miserend.hu.di.modules;

import android.app.Activity;

import com.frama.miserend.hu.di.components.HomeScreenActivityComponent;
import com.frama.miserend.hu.home.HomeScreenActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by Balazs on 2018. 02. 18..
 */

@Module
public abstract class ActivityBuilder {

    @Binds
    @IntoMap
    @ActivityKey(HomeScreenActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindHomeScreenActivity(HomeScreenActivityComponent.Builder builder);
/*
    @Binds
    @IntoMap
    @ActivityKey(ChurchDetailsActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindChurchDetailsActivity(DetailActivityComponent.Builder builder);
*/
}
