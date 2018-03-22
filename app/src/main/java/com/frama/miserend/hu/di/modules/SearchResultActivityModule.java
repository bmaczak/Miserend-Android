package com.frama.miserend.hu.di.modules;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;

import com.frama.miserend.hu.churchdetails.ChurchDetailsActivity;
import com.frama.miserend.hu.churchdetails.ChurchDetailsViewModel;
import com.frama.miserend.hu.database.local.LocalDatabase;
import com.frama.miserend.hu.database.miserend.MiserendDatabase;
import com.frama.miserend.hu.di.scopes.PerActivity;
import com.frama.miserend.hu.router.Router;
import com.frama.miserend.hu.search.result.SearchResultActivity;

import javax.inject.Named;

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
