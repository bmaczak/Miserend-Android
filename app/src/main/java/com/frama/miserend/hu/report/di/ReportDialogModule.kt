package com.frama.miserend.hu.report.di

import android.support.v4.app.Fragment

import com.frama.miserend.hu.di.scopes.PerFragment
import com.frama.miserend.hu.report.view.ReportDialogFragment

import dagger.Module
import dagger.Provides

/**
 * Created by Balazs on 2018. 02. 11..
 */
@Module
class ReportDialogModule {

    @PerFragment
    @Provides
    internal fun provideFragment(reportDialogFragment: ReportDialogFragment): Fragment {
        return reportDialogFragment
    }
}
