package com.frama.miserend.hu.di.modules.builder

import com.frama.miserend.hu.churchdetails.di.ChurchDetailsModule
import com.frama.miserend.hu.churchdetails.view.ChurchDetailsActivity
import com.frama.miserend.hu.di.modules.LocationModule
import com.frama.miserend.hu.di.modules.RouterModule
import com.frama.miserend.hu.di.scopes.PerActivity
import com.frama.miserend.hu.home.di.HomeScreenModule
import com.frama.miserend.hu.home.view.HomeScreenActivity
import com.frama.miserend.hu.search.advanced.AdvancedSearchActivity
import com.frama.miserend.hu.search.advanced.di.AdvancedSearchModule
import com.frama.miserend.hu.search.result.di.SearchResultModule
import com.frama.miserend.hu.search.result.view.SearchResultActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Balazs on 2018. 02. 18..
 */

@Module
abstract class ActivityBuilder {

    @PerActivity
    @ContributesAndroidInjector(modules = [HomeScreenModule::class, LocationModule::class, RouterModule::class])
    internal abstract fun bindHomeScreenActivity(): HomeScreenActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [ChurchDetailsModule::class, RouterModule::class])
    internal abstract fun bindChurchDetailsActivity(): ChurchDetailsActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [AdvancedSearchModule::class, RouterModule::class])
    internal abstract fun bindAdvancedSearchActivity(): AdvancedSearchActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [SearchResultModule::class, RouterModule::class])
    internal abstract fun bindSearchResultActivity(): SearchResultActivity
}
