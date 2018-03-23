package com.frama.miserend.hu.home.pages.masses.view;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.base.BaseFragment;
import com.frama.miserend.hu.database.miserend.relations.MassWithChurch;
import com.frama.miserend.hu.home.pages.masses.viewmodel.MassesViewModel;
import com.frama.miserend.hu.location.LocationRetriever;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Balazs on 2018. 02. 13..
 */

public class MassesFragment extends BaseFragment implements LocationRetriever.LocationResultListener {

    @Inject
    MassesViewModel massesViewModel;
    @Inject
    MassesAdapter adapter;
    @Inject
    LocationRetriever locationRetriever;

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

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
        locationRetriever.getLastKnownLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationRetriever.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onLocationRetrieved(Location location) {
        massesViewModel.getRecommendedMasses(location).observe(this, this::onMassesChanged);
        adapter.setCurrentLocation(location);
        recyclerView.setAdapter(adapter);
    }

    private void onMassesChanged(List<MassWithChurch> massWithChurches) {
        adapter.update(massWithChurches);
    }

    @Override
    public void onLocationError() {

    }
}
