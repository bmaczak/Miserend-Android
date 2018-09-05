package com.frama.miserend.hu.home.pages.masses.view;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.base.BaseFragment;
import com.frama.miserend.hu.database.miserend.relations.MassWithChurch;
import com.frama.miserend.hu.firebase.Analytics;
import com.frama.miserend.hu.home.pages.masses.viewmodel.MassesViewModel;
import com.frama.miserend.hu.location.LocationManager;
import com.frama.miserend.hu.router.Router;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Balazs on 2018. 02. 13..
 */

public class MassesFragment extends BaseFragment implements LocationManager.LocationResultListener, MassesAdapter.MassViewHolder.MassListActionListener {

    @Inject
    MassesViewModel massesViewModel;
    @Inject
    MassesAdapter adapter;
    @Inject
    LocationManager locationManager;
    @Inject
    Router router;
    @Inject
    Analytics analytics;

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.location_permission_layout)
    View locationPermissionLayout;
    @BindView(R.id.location_settings_layout)
    View locationSettingsLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_masses, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        locationManager.getLastKnownLocation();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        locationManager.registerListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        analytics.setCurrentScreen(getActivity(), Analytics.ScreenNames.MASSES);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        locationManager.unregisterListener(this);
    }

    @Override
    public void onLocationRetrieved(Location location) {
        massesViewModel.getRecommendedMasses(location).observe(this, this::onMassesChanged);
        adapter.setCurrentLocation(location);
        recyclerView.setVisibility(View.VISIBLE);
        locationSettingsLayout.setVisibility(View.GONE);
        locationPermissionLayout.setVisibility(View.GONE);
        recyclerView.setAdapter(adapter);
    }

    private void onMassesChanged(List<MassWithChurch> massWithChurches) {
        adapter.update(massWithChurches);
    }

    @Override
    public void onLocationError(LocationManager.LocationError locationError) {
        recyclerView.setVisibility(View.GONE);
        locationPermissionLayout.setVisibility(locationError == LocationManager.LocationError.PERMISSION ? View.VISIBLE : View.GONE);
        locationSettingsLayout.setVisibility(locationError == LocationManager.LocationError.COULD_NOT_RETRIEVE ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.location_settings_button)
    public void onLocationSettingsClicked() {
        locationManager.showLocationSettings();
    }

    @OnClick(R.id.location_permission_button)
    public void onLocationPermissionButtonClicked() {
        locationManager.requestPermission();
    }

    @Override
    public void onMassListItemClicked(MassWithChurch massWithChurch) {
        router.startGoogleNavigation(massWithChurch.getChurch());
    }

    @Override
    public void onChurchImageClicked(MassWithChurch massWithChurch) {
        router.showChurchDetails(massWithChurch.getChurch());
    }
}
