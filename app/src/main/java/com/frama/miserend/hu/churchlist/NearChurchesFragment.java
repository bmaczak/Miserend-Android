package com.frama.miserend.hu.churchlist;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.di.components.NearChurchesComponent;
import com.frama.miserend.hu.location.LocationRetriever;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Balazs on 2018. 02. 10..
 */

public class NearChurchesFragment extends Fragment implements LocationRetriever.LocationResultListener {

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @Inject
    NearChurchesViewModel viewModel;
    @Inject
    LocationRetriever locationRetriever;
    @Inject
    NearChurchesAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        NearChurchesComponent.Injector.inject(this);
        View v = inflater.inflate(R.layout.fragment_near_churches, container, false);
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
        viewModel.getNearestChurches(location.getLatitude(), location.getLongitude()).observe(this, adapter::setList);
        adapter.setCurrentLocation(location);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onLocationError() {

    }
}
