package com.frama.miserend.hu.di.modules;

import android.app.Activity;

import com.frama.miserend.hu.di.scopes.PerActivity;
import com.frama.miserend.hu.search.result.SearchResultActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Balazs on 2018. 02. 11..
 */
@Module
public class SearchResultModule {

    @PerActivity
    @Provides
    Activity provideActivity(SearchResultActivity searchResultActivity) {
        return searchResultActivity;
    }
}
