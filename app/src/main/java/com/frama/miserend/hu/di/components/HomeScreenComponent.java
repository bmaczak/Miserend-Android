package com.frama.miserend.hu.di.components;

import com.frama.miserend.hu.di.modules.HomeScreenModule;
import com.frama.miserend.hu.di.scopes.PerActivity;
import com.frama.miserend.hu.home.HomeScreenActivity;

import dagger.Component;

/**
 * Created by Balazs on 2018. 02. 12..
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = HomeScreenModule.class)
public interface HomeScreenComponent {
    void inject(HomeScreenActivity activity);

    final class Injector {
        private static HomeScreenComponent homeScreenComponent;

        private Injector() {
        }

        public static void inject(HomeScreenActivity activity) {
            homeScreenComponent = DaggerHomeScreenComponent.builder()
                    .appComponent(AppComponent.Injector.getComponent())
                    .homeScreenModule(new HomeScreenModule(activity))
                    .build();
            homeScreenComponent.inject(activity);
        }
    }
}
