package com.frama.miserend.hu.search.result.di;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;

import com.frama.miserend.hu.database.miserend.MiserendDatabase;
import com.frama.miserend.hu.di.modules.FavoritesModule;
import com.frama.miserend.hu.di.modules.LocationModule;
import com.frama.miserend.hu.di.scopes.PerActivity;
import com.frama.miserend.hu.di.scopes.PerFragment;
import com.frama.miserend.hu.router.Router;
import com.frama.miserend.hu.search.SearchParams;
import com.frama.miserend.hu.search.result.church.di.SearchResultChurchListModule;
import com.frama.miserend.hu.search.result.church.view.SearchResultChurchListFragment;
import com.frama.miserend.hu.search.result.mass.di.SearchResultMassListModule;
import com.frama.miserend.hu.search.result.mass.view.SearchResultMassListFragment;
import com.frama.miserend.hu.search.result.view.SearchResultActivity;
import com.frama.miserend.hu.search.result.viewmodel.SearchResultViewModel;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Balazs on 2018. 02. 11..
 */
@Module
public abstract class SearchResultModule {

    @PerActivity
    @Provides
    static Activity provideActivity(SearchResultActivity searchResultActivity) {
        return searchResultActivity;
    }

    @PerActivity
    @Provides
    static SearchResultViewModel provideSearchResultViewModel(SearchResultActivity activity, SearchResultViewModel.Factory factory) {
        return ViewModelProviders.of(activity, factory).get(SearchResultViewModel.class);
    }

    @PerActivity
    @Provides
    static SearchResultViewModel.Factory provideSearchResultViewModelFactory(@NonNull Application application, MiserendDatabase miserendDatabase, SearchParams searchParams) {
        return new SearchResultViewModel.Factory(application, miserendDatabase, searchParams);
    }

    @PerActivity
    @Provides
    static SearchParams provideSearchParams(SearchResultActivity activity) {
        return (SearchParams) activity.getIntent().getSerializableExtra(Router.IntentExtra.SEARCH_PARAMS);
    }


    @PerFragment
    @ContributesAndroidInjector(modules = {SearchResultChurchListModule.class, FavoritesModule.class})
    abstract SearchResultChurchListFragment bindSearchResultChurchListFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = {SearchResultMassListModule.class})
    abstract SearchResultMassListFragment bindSearchResultMassListFragment();

}
