package com.frama.miserend.hu.di.components;

import com.frama.miserend.hu.di.modules.FavoritesModule;
import com.frama.miserend.hu.di.scopes.PerFragment;
import com.frama.miserend.hu.home.pages.churches.favorites.FavoriteChurchesFragment;

import dagger.Component;

/**
 * Created by Balazs on 2018. 02. 11..
 */
@PerFragment
@Component(dependencies = AppComponent.class, modules = {FavoritesModule.class})
public interface FavoriteChurchesComponent {

    void inject(FavoriteChurchesFragment fragment);

    final class Injector {
        private static FavoriteChurchesComponent listComponent;

        private Injector() {
        }

        public static void inject(FavoriteChurchesFragment fragment) {
            listComponent = DaggerFavoriteChurchesComponent.builder()
                    .appComponent(AppComponent.Injector.getComponent())
                    .favoritesModule(new FavoritesModule(fragment))
                    .build();
            listComponent.inject(fragment);
        }
    }
}