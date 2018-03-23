package com.frama.miserend.hu.search.result.church.di;


import android.support.v4.app.Fragment;

import com.frama.miserend.hu.di.scopes.PerFragment;
import com.frama.miserend.hu.search.result.church.SearchResultChurchListFragment;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Balazs on 2018. 03. 22..
 */
@Module
public abstract class SearchResultChurchListModule {

    @PerFragment
    @Binds
    abstract Fragment fragment(SearchResultChurchListFragment fragment);
}
