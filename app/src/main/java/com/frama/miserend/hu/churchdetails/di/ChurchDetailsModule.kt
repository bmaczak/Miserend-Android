package com.frama.miserend.hu.churchdetails.di

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.ViewModelProviders

import com.frama.miserend.hu.churchdetails.view.ChurchDetailsActivity
import com.frama.miserend.hu.churchdetails.viewmodel.ChurchDetailsViewModel
import com.frama.miserend.hu.di.scopes.PerActivity
import com.frama.miserend.hu.di.scopes.PerFragment
import com.frama.miserend.hu.report.di.ReportDialogModule
import com.frama.miserend.hu.report.view.ReportDialogFragment
import com.frama.miserend.hu.repository.FavoritesRepository
import com.frama.miserend.hu.repository.MiserendRepository
import com.frama.miserend.hu.router.Router

import javax.inject.Named

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

/**
 * Created by Balazs on 2018. 02. 11..
 */
@Module
abstract class ChurchDetailsModule {

    @Module
    companion object {

        @JvmStatic
        @PerActivity
        @Provides
        internal fun provideActivity(churchDetailsActivity: ChurchDetailsActivity): Activity {
            return churchDetailsActivity
        }

        @JvmStatic
        @PerActivity
        @Provides
        internal fun provideChurchDetailsViewModel(churchDetailsActivity: ChurchDetailsActivity, factory: ChurchDetailsViewModel.Factory): ChurchDetailsViewModel {
            return ViewModelProviders.of(churchDetailsActivity, factory)
                    .get(ChurchDetailsViewModel::class.java)
        }

        @JvmStatic
        @PerActivity
        @Provides
        internal fun provideChurchDetailsViewModelFactory(application: Application, @Named("churchId") churchId: Int, miserendRepository: MiserendRepository, favoritesRepository: FavoritesRepository): ChurchDetailsViewModel.Factory {
            return ChurchDetailsViewModel.Factory(application, churchId, miserendRepository, favoritesRepository)
        }

        @JvmStatic
        @PerActivity
        @Provides
        @Named("churchId")
        internal fun provideChurchId(churchDetailsActivity: ChurchDetailsActivity): Int {
            return churchDetailsActivity.intent.getIntExtra(Router.IntentExtra.CHURCH_ID, 0)
        }
    }

    @PerFragment
    @ContributesAndroidInjector(modules = [ReportDialogModule::class])
    internal abstract fun bindReportDialogFragment(): ReportDialogFragment

}
