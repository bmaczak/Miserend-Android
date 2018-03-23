package com.frama.miserend.hu.home.pages.churches.near;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.home.pages.churches.view.ChurchListFragment;
import com.frama.miserend.hu.home.pages.churches.favorites.FavoritesViewModel;
import com.frama.miserend.hu.location.LocationRetriever;
import com.frama.miserend.hu.router.Router;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Balazs on 2018. 02. 10..
 */

public class NearChurchesFragment extends ChurchListFragment implements LocationRetriever.LocationResultListener {

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @Inject
    NearChurchesViewModel nearChurchesViewModel;
    @Inject
    FavoritesViewModel favoritesViewModel;
    @Inject
    LocationRetriever locationRetriever;
    @Inject
    NearChurchesAdapter adapter;
    @Inject
    Router router;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_near_churches, container, false);
        ButterKnife.bind(this, v);
        favoritesViewModel.getFavorites().observe(this, this::onFavoritesChanged);
        return v;
    }

    private void onFavoritesChanged(List<Integer> favorites) {
        adapter.setFavorites(favorites);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        locationRetriever.getLastKnownLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationRetriever.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onLocationRetrieved(Location location) {
        nearChurchesViewModel.getNearestChurches(location.getLatitude(), location.getLongitude()).observe(this, adapter::setList);
        adapter.setCurrentLocation(location);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onLocationError() {

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
