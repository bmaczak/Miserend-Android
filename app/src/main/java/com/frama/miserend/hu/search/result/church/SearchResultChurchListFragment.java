package com.frama.miserend.hu.search.result.church;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.frama.miserend.hu.base.BaseFragment;
import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses;
import com.frama.miserend.hu.router.Router;
import com.frama.miserend.hu.search.SearchParams;
import com.frama.miserend.hu.search.result.viewmodel.SearchResultViewModel;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Balazs on 2018. 03. 22..
 */

public class SearchResultChurchListFragment extends BaseFragment {

    @Inject
    SearchResultViewModel searchResultViewModel;

    public static SearchResultChurchListFragment newInstance(SearchParams searchParams) {
        SearchResultChurchListFragment fragment = new SearchResultChurchListFragment();
        Bundle params = new Bundle();
        params.putSerializable(Router.IntentExtra.SEARCH_PARAMS, searchParams);
        fragment.setArguments(params);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(container.getContext());
        textView.setText("Search result church list");
        SearchParams searchParams = (SearchParams) getArguments().getSerializable(Router.IntentExtra.SEARCH_PARAMS);
        searchResultViewModel.getChurchSearchResults(searchParams)
                .observe(this, this::onSearchResultsLoaded);
        return textView;
    }

    private void onSearchResultsLoaded(List<ChurchWithMasses> churchWithMasses) {
        Toast.makeText(getActivity(), "Search results: " + churchWithMasses.size(), Toast.LENGTH_SHORT).show();
    }
}
