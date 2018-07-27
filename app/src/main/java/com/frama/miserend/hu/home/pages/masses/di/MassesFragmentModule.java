package com.frama.miserend.hu.home.pages.masses.di;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;

import com.frama.miserend.hu.database.miserend.MiserendDatabase;
import com.frama.miserend.hu.di.scopes.PerFragment;
import com.frama.miserend.hu.home.pages.masses.view.MassesAdapter;
import com.frama.miserend.hu.home.pages.masses.view.MassesFragment;
import com.frama.miserend.hu.home.pages.masses.viewmodel.MassesViewModel;
import com.frama.miserend.hu.location.LocationManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Balazs on 2018. 02. 11..
 */
@Module
public class MassesFragmentModule {

    @PerFragment
    @Provides
    Fragment provideFragment(MassesFragment fragment) {
        return fragment;
    }

    @PerFragment
    @Provides
    MassesAdapter provideMassesAdapter(MassesFragment fragment) {
        return new MassesAdapter(fragment);
    }

    @PerFragment
    @Provides
    MassesViewModel provideMassesViewModel(MassesFragment fragment, MassesViewModel.Factory factory) {
        return ViewModelProviders.of(fragment, factory)
                .get(MassesViewModel.class);
    }

    @PerFragment
    @Provides
    MassesViewModel.Factory provideMassesViewModelFactory(Application application, MiserendDatabase database) {
        return new MassesViewModel.Factory(application, database);
    }

    @PerFragment
    @Provides
    LocationManager.LocationResultListener provideLocationResultListener(MassesFragment fragment) {
        return fragment;
    }

}
