package com.frama.miserend.hu.di.modules;

import android.support.v4.app.Fragment;

import com.frama.miserend.hu.di.components.FavoriteChurchesFragmentComponent;
import com.frama.miserend.hu.di.components.NearChurchesFragmentComponent;
import com.frama.miserend.hu.home.pages.churches.favorites.FavoriteChurchesFragment;
import com.frama.miserend.hu.home.pages.churches.near.NearChurchesFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

/**
 * Created by Balazs on 2018. 02. 18..
 */

@Module
public abstract class FragmentBuilder {

    @Binds
    @IntoMap
    @FragmentKey(NearChurchesFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> bindNearChurchesFragment(NearChurchesFragmentComponent.Builder builder);

    @Binds
    @IntoMap
    @FragmentKey(FavoriteChurchesFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> bindFavoriteChurchesFragment(FavoriteChurchesFragmentComponent.Builder builder);

}
