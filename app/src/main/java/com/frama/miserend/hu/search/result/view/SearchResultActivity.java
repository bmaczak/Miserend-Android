package com.frama.miserend.hu.search.result.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.base.FragmentHostActivity;
import com.frama.miserend.hu.router.Router;
import com.frama.miserend.hu.search.SearchParams;
import com.frama.miserend.hu.search.result.church.view.SearchResultChurchListFragment;
import com.frama.miserend.hu.search.result.mass.view.SearchResultMassListFragment;
import com.frama.miserend.hu.search.result.viewmodel.SearchResultViewModel;

import javax.inject.Inject;

/**
 * Created by Balazs on 2018. 03. 16..
 */

public class SearchResultActivity extends FragmentHostActivity {

    @Inject
    SearchResultViewModel searchResultViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        showFragment();
    }

    private void showFragment() {
        Fragment fragment = searchResultViewModel.shouldShowMasses() ? new SearchResultMassListFragment() : SearchResultChurchListFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
