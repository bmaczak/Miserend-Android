package com.frama.miserend.hu.home.pages.churches.near;

import android.arch.paging.PagedList;
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
import com.frama.miserend.hu.location.LocationError;
import com.frama.miserend.hu.location.LocationPermissionHelper;
import com.frama.miserend.hu.massdetails.view.MassDetailsDialogFragment;
import com.frama.miserend.hu.router.Router;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Balazs on 2018. 02. 10..
 */

public class NearChurchesFragment extends ChurchListFragment {

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.location_permission_layout)
    View locationPermissionLayout;
    @BindView(R.id.location_settings_layout)
    View locationSettingsLayout;

    @Inject
    NearChurchesViewModel nearChurchesViewModel;
    @Inject
    LocationPermissionHelper locationPermissionHelper;
    @Inject
    NearChurchesAdapter adapter;
    @Inject
    Router router;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_near_churches, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    private void onFavoritesChanged(List<Integer> favorites) {
        adapter.setFavorites(favorites);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        nearChurchesViewModel.getFavorites().observe(this, this::onFavoritesChanged);
        nearChurchesViewModel.getNearestChurches().observe(this, this::onChurchesLoaded);
        nearChurchesViewModel.getLocationError().observe(this, this::onLocationError);
    }

    private void onChurchesLoaded(PagedList<ChurchWithMasses> churchWithMasses) {
        recyclerView.setVisibility(View.VISIBLE);
        locationSettingsLayout.setVisibility(View.GONE);
        locationPermissionLayout.setVisibility(View.GONE);
        //adapter.setCurrentLocation(location);
        recyclerView.setAdapter(adapter);
        adapter.submitList(churchWithMasses);
    }

    public void onLocationError(LocationError locationError) {
        recyclerView.setVisibility(View.GONE);
        locationPermissionLayout.setVisibility(locationError == LocationError.PERMISSION ? View.VISIBLE : View.GONE);
        locationSettingsLayout.setVisibility(locationError == LocationError.COULD_NOT_RETRIEVE ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onChurchClicked(Church church) {
        router.showChurchDetails(church);
    }

    @Override
    public void onFavoriteClicked(Church church) {
        nearChurchesViewModel.toggleFavorite(church.getId());
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
    public void onMassClicked(Mass mass) {
        MassDetailsDialogFragment.newInstance(mass).show(getChildFragmentManager(), "mass_details");
    }
}
