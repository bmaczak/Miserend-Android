package com.frama.miserend.hu.di.modules;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.frama.miserend.hu.database.miserend.MiserendDatabase;
import com.frama.miserend.hu.di.scopes.PerActivity;
import com.frama.miserend.hu.di.scopes.PerFragment;
import com.frama.miserend.hu.search.result.SearchResultViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Balazs on 2018. 02. 11..
 */
@Module
public class SearchResultModule {

    @PerFragment
    @Provides
    SearchResultViewModel provideSearchResultViewModel(Fragment fragment, SearchResultViewModel.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(SearchResultViewModel.class);
    }

    @PerFragment
    @Provides
    SearchResultViewModel.Factory provideSearchResultViewModelFactory(@NonNull Application application, MiserendDatabase miserendDatabase) {
        return new SearchResultViewModel.Factory(application, miserendDatabase);
    }
}
