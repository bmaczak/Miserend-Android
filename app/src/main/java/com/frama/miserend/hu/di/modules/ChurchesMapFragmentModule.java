package com.frama.miserend.hu.di.modules;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;

import com.frama.miserend.hu.database.miserend.MiserendDatabase;
import com.frama.miserend.hu.di.scopes.PerFragment;
import com.frama.miserend.hu.home.pages.churches.near.NearChurchesAdapter;
import com.frama.miserend.hu.home.pages.churches.near.NearChurchesFragment;
import com.frama.miserend.hu.home.pages.churches.near.NearChurchesViewModel;
import com.frama.miserend.hu.home.pages.map.ChurchesMapFragment;
import com.frama.miserend.hu.home.pages.map.ChurchesMapViewModel;
import com.frama.miserend.hu.location.LocationRetriever;

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
