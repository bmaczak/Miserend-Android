package com.frama.miserend.hu.search.result.mass.di;


import android.support.v4.app.Fragment;

import com.frama.miserend.hu.di.scopes.PerFragment;
import com.frama.miserend.hu.home.pages.masses.view.MassesAdapter;
import com.frama.miserend.hu.home.pages.masses.view.MassesFragment;
import com.frama.miserend.hu.search.result.mass.view.SearchResultMassListFragment;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Balazs on 2018. 03. 22..
 */
@Module
public abstract class SearchResultMassListModule {

    @PerFragment
    @Binds
    abstract Fragment fragment(SearchResultMassListFragment fragment);

    @PerFragment
    @Provides
    static MassesAdapter provideMassesAdapter() {
        return new MassesAdapter();
    }
}
