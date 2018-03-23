package com.frama.miserend.hu.search.result.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.base.BaseActivity;
import com.frama.miserend.hu.router.Router;
import com.frama.miserend.hu.search.SearchParams;
import com.frama.miserend.hu.search.result.church.SearchResultChurchListFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Balazs on 2018. 03. 16..
 */

public class SearchResultActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        SearchParams searchParams = (SearchParams) getIntent().getSerializableExtra(Router.IntentExtra.SEARCH_PARAMS);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, SearchResultChurchListFragment.newInstance(searchParams))
                .commit();
    }
}
