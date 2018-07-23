package com.frama.miserend.hu.home.pages.churches.near;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.home.pages.churches.favorites.FavoritesViewModel;
import com.frama.miserend.hu.home.pages.churches.view.ChurchListFragment;
import com.frama.miserend.hu.location.LocationRetriever;
import com.frama.miserend.hu.router.Router;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Balazs on 2018. 02. 10..
 */

public class NearChurchesFragment extends ChurchListFragment implements LocationRetriever.LocationResultListener {

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.location_permission_layout)
    View locationPermissionLayout;
    @BindView(R.id.location_settings_layout)
    View locationSettingsLayout;

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (LocationRetriever.LOCATION_SETTINGS_REQUEST_CODE == requestCode) {
            locationRetriever.getLastKnownLocation();
        }
    }

    @Override
    public void onLocationRetrieved(Location location) {
        nearChurchesViewModel.getNearestChurches(location.getLatitude(), location.getLongitude()).observe(this, adapter::setList);
        recyclerView.setVisibility(View.VISIBLE);
        locationSettingsLayout.setVisibility(View.GONE);
        locationPermissionLayout.setVisibility(View.GONE);
        adapter.setCurrentLocation(location);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onLocationError(LocationRetriever.LocationError locationError) {
        recyclerView.setVisibility(View.GONE);
        locationPermissionLayout.setVisibility(locationError == LocationRetriever.LocationError.PERMISSION ? View.VISIBLE : View.GONE);
        locationSettingsLayout.setVisibility(locationError == LocationRetriever.LocationError.COULD_NOT_RETRIEVE ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onChurchClicked(Church church) {
        router.showChurchDetails(church);
    }

    @Override
    public void onFavoriteClicked(Church church) {
        favoritesViewModel.toggleFavorite(church.getId());
    }

    @OnClick(R.id.location_settings_button)
    public void onLocationSettingsClicked() {
        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), LocationRetriever.LOCATION_SETTINGS_REQUEST_CODE);
    }

    @OnClick(R.id.location_permission_button)
    public void onLocationPermissionButtonClicked() {
        locationRetriever.getLastKnownLocation();
    }
}
