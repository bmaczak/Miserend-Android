package com.frama.miserend.hu.di.modules.builder;

import com.frama.miserend.hu.churchdetails.view.ChurchDetailsActivity;
import com.frama.miserend.hu.search.advanced.di.AdvancedSearchModule;
import com.frama.miserend.hu.churchdetails.di.ChurchDetailsModule;
import com.frama.miserend.hu.home.di.HomeScreenModule;
import com.frama.miserend.hu.di.modules.RouterModule;
import com.frama.miserend.hu.search.result.di.SearchResultActivityModule;
import com.frama.miserend.hu.di.scopes.PerActivity;
import com.frama.miserend.hu.home.view.HomeScreenActivity;
import com.frama.miserend.hu.search.advanced.AdvancedSearchActivity;
import com.frama.miserend.hu.search.result.view.SearchResultActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Balazs on 2018. 02. 18..
 */

@Module
public abstract class ActivityBuilder {

    @PerActivity
    @ContributesAndroidInjector(modules = {HomeScreenModule.class, RouterModule.class})
    abstract HomeScreenActivity bindHomeScreenActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = {ChurchDetailsModule.class, RouterModule.class})
    abstract ChurchDetailsActivity bindChurchDetailsActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = {AdvancedSearchModule.class, RouterModule.class})
    abstract AdvancedSearchActivity bindAdvancedSearchActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = {SearchResultActivityModule.class, RouterModule.class})
    abstract SearchResultActivity bindSearchResultActivity();
}
