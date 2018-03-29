package com.frama.miserend.hu.search.advanced;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.base.BaseActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DaggerActivity;

/**
 * Created by maczak on 2018. 03. 14..
 */

public class AdvancedSearchActivity extends BaseActivity {

    @Inject
    AdvancedSearchViewModel advancedSearchViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);
    }
}
