package com.frama.miserend.hu.search.result.di;

import android.app.Activity;

import com.frama.miserend.hu.di.scopes.PerActivity;
import com.frama.miserend.hu.search.result.view.SearchResultActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Balazs on 2018. 02. 11..
 */
@Module
public class SearchResultActivityModule {

    @PerActivity
    @Provides
    Activity provideActivity(SearchResultActivity searchResultActivity) {
        return searchResultActivity;
    }
}
