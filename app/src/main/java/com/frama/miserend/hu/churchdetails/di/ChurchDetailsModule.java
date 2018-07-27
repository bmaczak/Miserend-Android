package com.frama.miserend.hu.churchdetails.di;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;

import com.frama.miserend.hu.churchdetails.view.ChurchDetailsActivity;
import com.frama.miserend.hu.churchdetails.viewmodel.ChurchDetailsViewModel;
import com.frama.miserend.hu.database.local.LocalDatabase;
import com.frama.miserend.hu.database.miserend.MiserendDatabase;
import com.frama.miserend.hu.di.scopes.PerActivity;
import com.frama.miserend.hu.di.scopes.PerFragment;
import com.frama.miserend.hu.report.di.ReportDialogModule;
import com.frama.miserend.hu.report.view.ReportDialogFragment;
import com.frama.miserend.hu.router.Router;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Balazs on 2018. 02. 11..
 */
@Module
public abstract class ChurchDetailsModule {

    @PerActivity
    @Provides
    static Activity provideActivity(ChurchDetailsActivity churchDetailsActivity) {
        return churchDetailsActivity;
    }

    @PerActivity
    @Provides
    static ChurchDetailsViewModel provideChurchDetailsViewModel(ChurchDetailsActivity churchDetailsActivity, ChurchDetailsViewModel.Factory factory) {
        return ViewModelProviders.of(churchDetailsActivity, factory)
                .get(ChurchDetailsViewModel.class);
    }

    @PerActivity
    @Provides
    static ChurchDetailsViewModel.Factory provideChurchDetailsViewModelFactory(Application application, @Named("churchId") int churchId, LocalDatabase localDatabase, MiserendDatabase miserendDatabase) {
        return new ChurchDetailsViewModel.Factory(application, churchId, localDatabase, miserendDatabase);
    }

    @PerActivity
    @Provides
    @Named("churchId")
    static int provideChurchId(ChurchDetailsActivity churchDetailsActivity) {
        return churchDetailsActivity.getIntent().getIntExtra(Router.IntentExtra.CHURCH_ID, 0);
    }

    @PerFragment
    @ContributesAndroidInjector(modules = {ReportDialogModule.class})
    abstract ReportDialogFragment bindReportDialogFragment();
}
