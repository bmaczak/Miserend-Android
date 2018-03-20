package com.frama.miserend.hu.di.modules;

import android.support.v4.app.Fragment;

import com.frama.miserend.hu.di.scopes.PerFragment;
import com.frama.miserend.hu.report.ReportDialogFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Balazs on 2018. 02. 11..
 */
@Module
public class ReportDialogModule {

    @PerFragment
    @Provides
    Fragment provideFragment(ReportDialogFragment reportDialogFragment) {
        return reportDialogFragment;
    }
}
