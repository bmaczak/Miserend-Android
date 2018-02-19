package com.frama.miserend.hu.di.components;

import com.frama.miserend.hu.di.modules.FavoritesModule;
import com.frama.miserend.hu.di.modules.LocationModule;
import com.frama.miserend.hu.di.modules.NearChurchesFragmentModule;
import com.frama.miserend.hu.di.scopes.PerFragment;
import com.frama.miserend.hu.home.pages.churches.near.NearChurchesFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by Balazs on 2018. 02. 11..
 */
@PerFragment
@Subcomponent(modules = {NearChurchesFragmentModule.class, LocationModule.class, FavoritesModule.class})
public interface NearChurchesFragmentComponent extends AndroidInjector<NearChurchesFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<NearChurchesFragment> {
        public abstract NearChurchesFragmentComponent.Builder nearChurchesModule(NearChurchesFragmentModule nearChurchesFragmentModule);
        public abstract NearChurchesFragmentComponent.Builder locationModule(LocationModule locationModule);
        public abstract NearChurchesFragmentComponent.Builder favoritesModule(FavoritesModule favoritesModule);
    }
}