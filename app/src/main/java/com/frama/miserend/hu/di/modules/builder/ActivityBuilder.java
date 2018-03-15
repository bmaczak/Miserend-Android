package com.frama.miserend.hu.di.modules.builder;

import com.frama.miserend.hu.churchdetails.ChurchDetailsActivity;
import com.frama.miserend.hu.di.modules.AdvancedSearchModule;
import com.frama.miserend.hu.di.modules.ChurchDetailsModule;
import com.frama.miserend.hu.di.modules.HomeScreenModule;
import com.frama.miserend.hu.di.modules.RouterModule;
import com.frama.miserend.hu.di.scopes.PerActivity;
import com.frama.miserend.hu.home.HomeScreenActivity;
import com.frama.miserend.hu.search.advanced.AdvancedSearchActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Balazs on 2018. 02. 18..
 */

@Module
public abstract class ActivityBuilder {

    @PerActivity
    @ContributesAndroidInjector(modules = {HomeScreenModule.class, FragmentBuilder.class, RouterModule.class})
    abstract HomeScreenActivity bindHomeScreenActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = {ChurchDetailsModule.class, FragmentBuilder.class, RouterModule.class})
    abstract ChurchDetailsActivity bindChurchDetailsActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = {AdvancedSearchModule.class, FragmentBuilder.class, RouterModule.class})
    abstract AdvancedSearchActivity bindAdvancedSearchActivity();
}
