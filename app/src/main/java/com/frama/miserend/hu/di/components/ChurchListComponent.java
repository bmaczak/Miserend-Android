package com.frama.miserend.hu.di.components;

import com.frama.miserend.hu.churchlist.NearChurchesFragment;
import com.frama.miserend.hu.di.modules.NearChurchesModule;
import com.frama.miserend.hu.di.scopes.PerFragment;

import dagger.Component;

/**
 * Created by Balazs on 2018. 02. 11..
 */
@PerFragment
@Component(dependencies = AppComponent.class, modules = NearChurchesModule.class)
public interface ChurchListComponent {

    void inject(NearChurchesFragment fragment);

    final class Injector {
        private static ChurchListComponent listComponent;

        private Injector() {
        }

        public static void inject(NearChurchesFragment fragment) {
            listComponent = DaggerChurchListComponent.builder()
                    .appComponent(AppComponent.Injector.getComponent())
                    .nearChurchesModule(new NearChurchesModule(fragment))
                    .build();
            listComponent.inject(fragment);
        }
    }
}