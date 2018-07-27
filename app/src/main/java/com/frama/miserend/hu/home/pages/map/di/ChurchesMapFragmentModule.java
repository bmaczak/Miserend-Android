package com.frama.miserend.hu.home.pages.map.di;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;

import com.frama.miserend.hu.database.miserend.MiserendDatabase;
import com.frama.miserend.hu.di.scopes.PerFragment;
import com.frama.miserend.hu.home.pages.map.view.ChurchesMapFragment;
import com.frama.miserend.hu.home.pages.map.viewmodel.ChurchesMapViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Balazs on 2018. 02. 11..
 */
@Module
public class ChurchesMapFragmentModule {

    @PerFragment
    @Provides
    Fragment provideFragment(ChurchesMapFragment fragment) {
        return fragment;
    }

    @PerFragment
    @Provides
    ChurchesMapViewModel provideChurchesMapViewModel(Fragment fragment, ChurchesMapViewModel.Factory factory) {
        return ViewModelProviders.of(fragment, factory)
                .get(ChurchesMapViewModel.class);
    }

    @PerFragment
    @Provides
    ChurchesMapViewModel.Factory provideChurchesMapViewModelFactory(Application application, MiserendDatabase database) {
        return new ChurchesMapViewModel.Factory(application, database);
    }
}
