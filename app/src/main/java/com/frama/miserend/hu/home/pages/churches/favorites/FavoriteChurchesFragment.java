package com.frama.miserend.hu.home.pages.churches.favorites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.home.pages.churches.ChurchListFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Balazs on 2018. 02. 17..
 */

public class FavoriteChurchesFragment extends ChurchListFragment {

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @Inject
    FavoritesViewModel favoritesViewModel;

    @Inject
    FavoriteChurchesAdapter favoriteChurchesAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorite_churches, container, false);
        ButterKnife.bind(this, v);
        recyclerView.setAdapter(favoriteChurchesAdapter);
        favoritesViewModel.getFavorites().observe(this, this::onFavoritesChanged);
        return v;
    }

    private void onFavoritesChanged(List<Integer> integers) {
        favoritesViewModel.getFavoriteChurches().observe(this, churches -> favoriteChurchesAdapter.update(churches));
    }

    @Override
    public void onChurchClicked(Church church) {

    }

    @Override
    public void onFavoriteClicked(Church church) {
        favoritesViewModel.toggleFavorite(church.getId());
    }
}
