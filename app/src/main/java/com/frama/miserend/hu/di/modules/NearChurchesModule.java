package com.frama.miserend.hu.di.modules;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;

import com.frama.miserend.hu.home.pages.churches.near.NearChurchesAdapter;
import com.frama.miserend.hu.home.pages.churches.near.NearChurchesFragment;
import com.frama.miserend.hu.home.pages.churches.near.NearChurchesViewModel;
import com.frama.miserend.hu.database.miserend.MiserendDatabase;
import com.frama.miserend.hu.di.scopes.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Balazs on 2018. 02. 11..
 */
@Module
public class NearChurchesModule {

    private NearChurchesFragment fragment;

    public NearChurchesModule(NearChurchesFragment fragment) {
        this.fragment = fragment;
    }

    @PerFragment
    @Provides
    NearChurchesAdapter provideNearChurchesAdapter() {
        return new NearChurchesAdapter(fragment);
    }

    @PerFragment
    @Provides
    NearChurchesViewModel provideNearChurchesViewModel(NearChurchesViewModel.Factory factory) {
        return ViewModelProviders.of(fragment, factory)
                .get(NearChurchesViewModel.class);
    }

    @PerFragment
    @Provides
    NearChurchesViewModel.Factory provideNearChurchesViewModelFactory(Application application, MiserendDatabase database) {
        return new NearChurchesViewModel.Factory(application, database);
    }

}
