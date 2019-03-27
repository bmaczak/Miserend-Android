package com.frama.miserend.hu.di.modules

import android.content.Context

import com.frama.miserend.hu.database.local.LocalDatabase
import com.frama.miserend.hu.database.miserend.MiserendDatabase
import com.frama.miserend.hu.di.qualifiers.ApplicationContext
import com.frama.miserend.hu.location.LocationRepository
import com.frama.miserend.hu.repository.FavoritesRepository
import com.frama.miserend.hu.repository.MiserendRepository
import com.frama.miserend.hu.repository.RecentSearchesRepository

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

/**
 * Created by maczak on 2018. 02. 16..
 */
@Module
class RepositoryModule {

    @Singleton
    @Provides
    internal fun provideMiserendRepository(miserendDatabase: MiserendDatabase): MiserendRepository {
        return MiserendRepository(miserendDatabase.churchDao(), miserendDatabase.churchWithMassesDao(), miserendDatabase.massesDao())
    }

    @Singleton
    @Provides
    internal fun provideFavoritesRepository(localDatabase: LocalDatabase): FavoritesRepository {
        return FavoritesRepository(localDatabase.favoritesDao())
    }

    @Singleton
    @Provides
    internal fun provideRecentSearchesRepository(localDatabase: LocalDatabase): RecentSearchesRepository {
        return RecentSearchesRepository(localDatabase.recentSearchesDao())
    }

    @Provides
    @Singleton
    internal fun provideLocationRepository(@ApplicationContext context: Context): LocationRepository {
        return LocationRepository(context)
    }
}
