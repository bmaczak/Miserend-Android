package com.frama.miserend.hu.search.result.di

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.ViewModelProviders

import com.frama.miserend.hu.di.scopes.PerActivity
import com.frama.miserend.hu.di.scopes.PerFragment
import com.frama.miserend.hu.repository.FavoritesRepository
import com.frama.miserend.hu.repository.MiserendRepository
import com.frama.miserend.hu.router.Router
import com.frama.miserend.hu.search.SearchParams
import com.frama.miserend.hu.search.result.church.di.SearchResultChurchListModule
import com.frama.miserend.hu.search.result.church.view.SearchResultChurchListFragment
import com.frama.miserend.hu.search.result.mass.di.SearchResultMassListModule
import com.frama.miserend.hu.search.result.mass.view.SearchResultMassListFragment
import com.frama.miserend.hu.search.result.view.SearchResultActivity
import com.frama.miserend.hu.search.result.viewmodel.SearchResultViewModel

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

/**
 * Created by Balazs on 2018. 02. 11..
 */
@Module
abstract class SearchResultModule {

    @Module
    companion object {

        @JvmStatic
        @PerActivity
        @Provides
        internal fun provideActivity(searchResultActivity: SearchResultActivity): Activity {
            return searchResultActivity
        }

        @JvmStatic
        @PerActivity
        @Provides
        internal fun provideSearchResultViewModel(activity: SearchResultActivity, factory: SearchResultViewModel.Factory): SearchResultViewModel {
            return ViewModelProviders.of(activity, factory).get(SearchResultViewModel::class.java)
        }

        @JvmStatic
        @PerActivity
        @Provides
        internal fun provideSearchResultViewModelFactory(application: Application, miserendRepository: MiserendRepository, favoritesRepository: FavoritesRepository, searchParams: SearchParams): SearchResultViewModel.Factory {
            return SearchResultViewModel.Factory(application, miserendRepository, favoritesRepository, searchParams)
        }

        @JvmStatic
        @PerActivity
        @Provides
        internal fun provideSearchParams(activity: SearchResultActivity): SearchParams {
            return activity.intent.getSerializableExtra(Router.IntentExtra.SEARCH_PARAMS) as SearchParams
        }
    }

    @PerFragment
    @ContributesAndroidInjector(modules = [SearchResultChurchListModule::class])
    internal abstract fun bindSearchResultChurchListFragment(): SearchResultChurchListFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [SearchResultMassListModule::class])
    internal abstract fun bindSearchResultMassListFragment(): SearchResultMassListFragment
}
