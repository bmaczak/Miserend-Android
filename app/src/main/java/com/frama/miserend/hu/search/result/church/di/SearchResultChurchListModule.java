package com.frama.miserend.hu.search.result.church.di;


import android.support.v4.app.Fragment;

import com.frama.miserend.hu.di.scopes.PerFragment;
import com.frama.miserend.hu.home.pages.churches.view.ChurchViewHolder;
import com.frama.miserend.hu.search.result.church.view.ChurchSearchResultAdapter;
import com.frama.miserend.hu.search.result.church.view.SearchResultChurchListFragment;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Balazs on 2018. 03. 22..
 */
@Module
public abstract class SearchResultChurchListModule {

    @PerFragment
    @Binds
    abstract Fragment fragment(SearchResultChurchListFragment fragment);

    @PerFragment
    @Binds
    abstract ChurchViewHolder.ChurchListActionListener churchListActionListener(SearchResultChurchListFragment fragment);

    @PerFragment
    @Provides
    static ChurchSearchResultAdapter provideAdapter(ChurchViewHolder.ChurchListActionListener churchListActionListener) {
        return new ChurchSearchResultAdapter(churchListActionListener);
    }
}
