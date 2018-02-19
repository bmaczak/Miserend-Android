package com.frama.miserend.hu.di.components;

import com.frama.miserend.hu.di.modules.FragmentBuilder;
import com.frama.miserend.hu.di.modules.HomeScreenModule;
import com.frama.miserend.hu.di.modules.RouterModule;
import com.frama.miserend.hu.di.scopes.PerActivity;
import com.frama.miserend.hu.home.HomeScreenActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * Created by Balazs on 2018. 02. 12..
 */
@PerActivity
@Subcomponent(modules = {AndroidInjectionModule.class, HomeScreenModule.class, FragmentBuilder.class, RouterModule.class})
public interface HomeScreenActivityComponent extends AndroidInjector<HomeScreenActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<HomeScreenActivity> {
    }
}
