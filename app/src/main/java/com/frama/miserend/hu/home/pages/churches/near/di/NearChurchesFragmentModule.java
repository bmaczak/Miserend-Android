package com.frama.miserend.hu.home.pages.churches.near.di;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;

import com.frama.miserend.hu.database.miserend.MiserendDatabase;
import com.frama.miserend.hu.di.scopes.PerFragment;
import com.frama.miserend.hu.home.pages.churches.near.NearChurchesAdapter;
import com.frama.miserend.hu.home.pages.churches.near.NearChurchesFragment;
import com.frama.miserend.hu.home.pages.churches.near.NearChurchesViewModel;
import com.frama.miserend.hu.location.LocationManager;
import com.frama.miserend.hu.repository.FavoritesRepository;
import com.frama.miserend.hu.repository.MiserendRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Balazs on 2018. 02. 11..
 */
@Module
public class NearChurchesFragmentModule {

    @PerFragment
    @Provides
    Fragment provideFragment(NearChurchesFragment fragment) {
        return fragment;
    }

    @PerFragment
    @Provides
    NearChurchesAdapter provideNearChurchesAdapter(NearChurchesFragment fragment) {
        return new NearChurchesAdapter(fragment);
    }

    @PerFragment
    @Provides
    NearChurchesViewModel provideNearChurchesViewModel(NearChurchesFragment fragment, NearChurchesViewModel.Factory factory) {
        return ViewModelProviders.of(fragment, factory)
                .get(NearChurchesViewModel.class);
    }

    @PerFragment
    @Provides
    NearChurchesViewModel.Factory provideNearChurchesViewModelFactory(Application application, MiserendRepository miserendRepository, FavoritesRepository favoritesRepository) {
        return new NearChurchesViewModel.Factory(application, miserendRepository, favoritesRepository);
    }

    @PerFragment
    @Provides
    LocationManager.LocationResultListener provideLocationResultListener(NearChurchesFragment fragment) {
        return fragment;
    }

}
