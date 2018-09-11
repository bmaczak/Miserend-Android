package com.frama.miserend.hu.home.pages.masses.view;

import android.content.Context;
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
import com.frama.miserend.hu.location.LocationError;
import com.frama.miserend.hu.location.LocationPermissionHelper;
import com.frama.miserend.hu.router.Router;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Balazs on 2018. 02. 13..
 */

public class MassesFragment extends BaseFragment implements MassesAdapter.MassViewHolder.MassListActionListener {

    @Inject
    MassesViewModel massesViewModel;
    @Inject
    MassesAdapter adapter;
    @Inject
    LocationPermissionHelper locationPermissionHelper;
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
    @BindView(R.id.no_masses_layout)
    View noMassesLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_masses, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        massesViewModel.getRecommendedMasses().observe(this, this::onMassesChanged);
        massesViewModel.getLocationError().observe(this, this::onLocationError);
    }

    @Override
    public void onResume() {
        super.onResume();
        analytics.setCurrentScreen(getActivity(), Analytics.ScreenNames.MASSES);
    }

    private void onMassesChanged(List<MassWithChurch> massWithChurches) {
        //adapter.setCurrentLocation(location);
        if (massWithChurches.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            noMassesLayout.setVisibility(View.VISIBLE);
        } else {
            noMassesLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            locationSettingsLayout.setVisibility(View.GONE);
            locationPermissionLayout.setVisibility(View.GONE);
            recyclerView.setAdapter(adapter);
            adapter.update(massWithChurches);
        }
    }

    private void onLocationError(LocationError locationError) {
        recyclerView.setVisibility(View.GONE);
        locationPermissionLayout.setVisibility(locationError == LocationError.PERMISSION ? View.VISIBLE : View.GONE);
        locationSettingsLayout.setVisibility(locationError == LocationError.COULD_NOT_RETRIEVE ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.location_settings_button)
    public void onLocationSettingsClicked() {
        locationPermissionHelper.showLocationSettings();
    }

    @OnClick(R.id.location_permission_button)
    public void onLocationPermissionButtonClicked() {
        locationPermissionHelper.requestPermission();
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
