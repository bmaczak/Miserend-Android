package com.frama.miserend.hu.home.pages.churches.favorites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.database.miserend.entities.Mass;
import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses;
import com.frama.miserend.hu.home.pages.churches.view.ChurchListFragment;
import com.frama.miserend.hu.massdetails.view.MassDetailsDialogFragment;
import com.frama.miserend.hu.router.Router;

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
    @BindView(R.id.no_favorites)
    View noFavoritesView;

    @Inject
    FavoritesViewModel favoritesViewModel;
    @Inject
    FavoriteChurchesAdapter favoriteChurchesAdapter;
    @Inject
    Router router;

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
        favoritesViewModel.getFavoriteChurches().observe(this, this::onFavoritesLoaded);
    }

    public void onFavoritesLoaded(List<ChurchWithMasses> churches) {
        if (churches != null && !churches.isEmpty()) {
            noFavoritesView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            favoriteChurchesAdapter.update(churches);
        } else {
            noFavoritesView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onChurchClicked(Church church) {
        router.showChurchDetails(church);
    }

    @Override
    public void onFavoriteClicked(Church church) {
        favoritesViewModel.toggleFavorite(church.getId());
    }

    @Override
    public void onMassClicked(Mass mass) {
        MassDetailsDialogFragment.newInstance(mass).show(getChildFragmentManager(), "mass_details");
    }
}
