package com.frama.miserend.hu.search.result.mass.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frama.miserend.hu.base.BaseFragment;
import com.frama.miserend.hu.database.miserend.relations.MassWithChurch;
import com.frama.miserend.hu.home.pages.masses.view.MassesAdapter;
import com.frama.miserend.hu.search.result.viewmodel.SearchResultViewModel;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Balazs on 2018. 04. 06..
 */

public class SearchResultMassListFragment extends BaseFragment {

    @Inject
    SearchResultViewModel searchResultViewModel;
    @Inject
    MassesAdapter adapter;

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recyclerView = new RecyclerView(container.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        searchResultViewModel.getMassSearchResults().observe(this, this::onMassesChanged);
        return recyclerView;
    }

    private void onMassesChanged(List<MassWithChurch> massWithChurches) {
        adapter.update(massWithChurches);
    }
}
