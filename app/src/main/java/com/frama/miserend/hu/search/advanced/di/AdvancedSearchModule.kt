package com.frama.miserend.hu.search.advanced.di

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.ViewModelProviders

import com.frama.miserend.hu.di.scopes.PerActivity
import com.frama.miserend.hu.repository.MiserendRepository
import com.frama.miserend.hu.search.advanced.AdvancedSearchActivity
import com.frama.miserend.hu.search.advanced.AdvancedSearchViewModel

import dagger.Module
import dagger.Provides

/**
 * Created by Balazs on 2018. 02. 11..
 */
@Module
class AdvancedSearchModule {

    @Module
    companion object {

        @JvmStatic
        @PerActivity
        @Provides
        internal fun provideAdvancedSearchViewModel(advancedSearchActivity: AdvancedSearchActivity, factory: AdvancedSearchViewModel.Factory): AdvancedSearchViewModel {
            return ViewModelProviders.of(advancedSearchActivity, factory)
                    .get(AdvancedSearchViewModel::class.java)
        }

        @JvmStatic
        @PerActivity
        @Provides
        internal fun provideAdvancedSearchViewModelFactory(applicatio: Application, miserendRepository: MiserendRepository): AdvancedSearchViewModel.Factory {
            return AdvancedSearchViewModel.Factory(applicatio, miserendRepository)
        }
    }

    @PerActivity
    @Provides
    internal fun provideActivity(homeScreenActivity: AdvancedSearchActivity): Activity {
        return homeScreenActivity
    }
}
