package com.frama.miserend.hu.di.modules;

import android.content.Context;

import com.frama.miserend.hu.database.local.LocalDatabase;
import com.frama.miserend.hu.database.miserend.MiserendDatabase;
import com.frama.miserend.hu.di.qualifiers.ApplicationContext;
import com.frama.miserend.hu.location.LocationRepository;
import com.frama.miserend.hu.repository.FavoritesRepository;
import com.frama.miserend.hu.repository.MiserendRepository;
import com.frama.miserend.hu.repository.RecentSearchesRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by maczak on 2018. 02. 16..
 */
@Module
public class RepositoryModule {

    @Singleton
    @Provides
    MiserendRepository provideMiserendRepository(MiserendDatabase miserendDatabase) {
        return new MiserendRepository(miserendDatabase.churchDao(), miserendDatabase.churchWithMassesDao(), miserendDatabase.massesDao());
    }

    @Singleton
    @Provides
    FavoritesRepository provideFavoritesRepository(LocalDatabase localDatabase) {
        return new FavoritesRepository(localDatabase.favoritesDao());
    }

    @Singleton
    @Provides
    RecentSearchesRepository provideRecentSearchesRepository(LocalDatabase localDatabase) {
        return new RecentSearchesRepository(localDatabase.recentSearchesDao());
    }

    @Provides
    @Singleton
    LocationRepository provideLocationRepository(@ApplicationContext Context context) {
        return new LocationRepository(context);
    }
}
