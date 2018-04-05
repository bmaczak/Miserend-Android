package com.frama.miserend.hu.search.result.church.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses;
import com.frama.miserend.hu.home.pages.churches.favorites.FavoritesViewModel;
import com.frama.miserend.hu.home.pages.churches.view.ChurchListFragment;
import com.frama.miserend.hu.router.Router;
import com.frama.miserend.hu.search.SearchParams;
import com.frama.miserend.hu.search.result.viewmodel.SearchResultViewModel;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Balazs on 2018. 03. 22..
 */

public class SearchResultChurchListFragment extends ChurchListFragment {

    @Inject
    SearchResultViewModel searchResultViewModel;
    @Inject
    FavoritesViewModel favoritesViewModel;
    @Inject
    Router router;
    @Inject
    ChurchSearchResultAdapter adapter;

    RecyclerView recyclerView;

    public static SearchResultChurchListFragment newInstance() {
        return new SearchResultChurchListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recyclerView = new RecyclerView(container.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        favoritesViewModel.getFavorites().observe(this, this::onFavoritesLoaded);
        searchResultViewModel.getChurchSearchResults().observe(this, this::onSearchResultsLoaded);
    }

    private void onFavoritesLoaded(List<Integer> favorites) {
        adapter.setFavorites(favorites);
    }

    private void onSearchResultsLoaded(List<ChurchWithMasses> churchWithMasses) {
        adapter.update(churchWithMasses);
    }

    @Override
    public void onChurchClicked(Church church) {
        router.showChurchDetails(church);
    }

    @Override
    public void onFavoriteClicked(Church church) {
        favoritesViewModel.toggleFavorite(church.getId());
    }
}
