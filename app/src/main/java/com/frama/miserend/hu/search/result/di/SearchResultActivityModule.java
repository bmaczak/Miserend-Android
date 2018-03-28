package com.frama.miserend.hu.search.result.di;

import android.app.Activity;

import com.frama.miserend.hu.di.modules.FavoritesModule;
import com.frama.miserend.hu.di.scopes.PerActivity;
import com.frama.miserend.hu.di.scopes.PerFragment;
import com.frama.miserend.hu.search.result.church.di.SearchResultChurchListModule;
import com.frama.miserend.hu.search.result.church.view.SearchResultChurchListFragment;
import com.frama.miserend.hu.search.result.view.SearchResultActivity;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Balazs on 2018. 02. 11..
 */
@Module
public abstract class SearchResultActivityModule {

    @PerActivity
    @Provides
    static Activity provideActivity(SearchResultActivity searchResultActivity) {
        return searchResultActivity;
    }

    @PerFragment
    @ContributesAndroidInjector(modules = {SearchResultChurchListModule.class, SearchResultModule.class, FavoritesModule.class})
    abstract SearchResultChurchListFragment bindSearchResultChurchListFragment();
}
