package com.frama.miserend.hu.di.components

import android.app.Application

import com.frama.miserend.hu.application.MiserendApplication
import com.frama.miserend.hu.application.di.ApiModule
import com.frama.miserend.hu.application.di.ApplicationModule
import com.frama.miserend.hu.di.modules.DatabaseModule
import com.frama.miserend.hu.di.modules.RepositoryModule
import com.frama.miserend.hu.di.modules.builder.ActivityBuilder

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

/**
 * Created by maczak on 2018. 02. 09..
 */

@Singleton
@Component(modules = [AndroidInjectionModule::class, ApplicationModule::class, DatabaseModule::class, RepositoryModule::class, ApiModule::class, ActivityBuilder::class])
interface AppComponent {

    fun inject(application: MiserendApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
