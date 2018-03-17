package com.frama.miserend.hu.search.result;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.base.BaseActivity;

/**
 * Created by Balazs on 2018. 03. 16..
 */

public class SearchResultActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
    }
}
