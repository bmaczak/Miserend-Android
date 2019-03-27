package com.frama.miserend.hu.home.di

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.ViewModelProviders

import com.frama.miserend.hu.database.miserend.manager.DatabaseManager
import com.frama.miserend.hu.di.scopes.PerActivity
import com.frama.miserend.hu.di.scopes.PerFragment
import com.frama.miserend.hu.home.pages.churches.favorites.FavoriteChurchesFragment
import com.frama.miserend.hu.home.pages.churches.favorites.di.FavoritesFragmentModule
import com.frama.miserend.hu.home.pages.churches.near.NearChurchesFragment
import com.frama.miserend.hu.home.pages.churches.near.di.NearChurchesFragmentModule
import com.frama.miserend.hu.home.pages.churches.view.ChurchesFragment
import com.frama.miserend.hu.home.pages.map.di.ChurchesMapFragmentModule
import com.frama.miserend.hu.home.pages.map.view.ChurchesMapFragment
import com.frama.miserend.hu.home.pages.masses.di.MassesFragmentModule
import com.frama.miserend.hu.home.pages.masses.view.MassesFragment
import com.frama.miserend.hu.home.view.HomeScreenActivity
import com.frama.miserend.hu.home.viewmodel.HomeViewModel
import com.frama.miserend.hu.location.LocationRepository
import com.frama.miserend.hu.preferences.Preferences
import com.frama.miserend.hu.repository.MiserendRepository
import com.frama.miserend.hu.repository.RecentSearchesRepository
import com.frama.miserend.hu.search.suggestions.viewmodel.SuggestionViewModel

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

/**
 * Created by Balazs on 2018. 02. 11..
 */
@Module
abstract class HomeScreenModule {

    @Module
    companion object {

        @JvmStatic
        @PerActivity
        @Provides
        internal fun provideActivity(homeScreenActivity: HomeScreenActivity): Activity {
            return homeScreenActivity
        }

        @JvmStatic
        @PerActivity
        @Provides
        internal fun provideHomeViewModel(homeScreenActivity: HomeScreenActivity, factory: HomeViewModel.Factory): HomeViewModel {
            return ViewModelProviders.of(homeScreenActivity, factory)
                    .get(HomeViewModel::class.java)
        }

        @JvmStatic
        @PerActivity
        @Provides
        internal fun provideHomeViewModelFactory(application: Application, databaseManager: DatabaseManager, preferences: Preferences, locationRepository: LocationRepository): HomeViewModel.Factory {
            return HomeViewModel.Factory(application, databaseManager, preferences, locationRepository)
        }

        @JvmStatic
        @PerActivity
        @Provides
        internal fun provideSuggestionViewModel(homeScreenActivity: HomeScreenActivity, factory: SuggestionViewModel.Factory): SuggestionViewModel {
            return ViewModelProviders.of(homeScreenActivity, factory)
                    .get(SuggestionViewModel::class.java)
        }

        @JvmStatic
        @PerActivity
        @Provides
        internal fun provideSuggestionViewModelFactory(application: Application, miserendRepository: MiserendRepository, recentSearchesRepository: RecentSearchesRepository): SuggestionViewModel.Factory {
            return SuggestionViewModel.Factory(application, miserendRepository, recentSearchesRepository)
        }
    }

    @PerFragment
    @ContributesAndroidInjector
    internal abstract fun bindChurchesFragment(): ChurchesFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [NearChurchesFragmentModule::class])
    internal abstract fun bindNearChurchesFragment(): NearChurchesFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [FavoritesFragmentModule::class])
    internal abstract fun bindFavoriteChurchesFragment(): FavoriteChurchesFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [MassesFragmentModule::class])
    internal abstract fun bindMassesFragment(): MassesFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [ChurchesMapFragmentModule::class])
    internal abstract fun bindChurchesMapFragment(): ChurchesMapFragment

}
