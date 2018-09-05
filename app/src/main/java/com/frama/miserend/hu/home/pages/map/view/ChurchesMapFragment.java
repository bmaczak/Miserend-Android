package com.frama.miserend.hu.home.pages.map.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.database.miserend.entities.Mass;
import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses;
import com.frama.miserend.hu.firebase.Analytics;
import com.frama.miserend.hu.home.pages.churches.favorites.FavoritesViewModel;
import com.frama.miserend.hu.home.pages.churches.filter.MassFilter;
import com.frama.miserend.hu.home.pages.churches.view.ChurchViewHolder;
import com.frama.miserend.hu.home.pages.map.viewmodel.ChurchesMapViewModel;
import com.frama.miserend.hu.location.LocationManager;
import com.frama.miserend.hu.massdetails.view.MassDetailsDialogFragment;
import com.frama.miserend.hu.router.Router;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import net.sharewire.googlemapsclustering.Cluster;
import net.sharewire.googlemapsclustering.ClusterManager;
import net.sharewire.googlemapsclustering.DefaultIconGenerator;
import net.sharewire.googlemapsclustering.IconStyle;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by Balazs on 2018. 02. 13..
 */

public class ChurchesMapFragment extends Fragment implements OnMapReadyCallback, ClusterManager.Callbacks<ChurchClusterItem>, LocationManager.LocationResultListener, ChurchViewHolder.ChurchListActionListener {

    private static int CURRENT_LOCATION_ZOOM = 14;

    @BindView(R.id.church_card_container)
    View churchCardContainer;

    @Inject
    ChurchesMapViewModel churchesMapViewModel;
    @Inject
    Router router;
    @Inject
    LocationManager locationManager;
    @Inject
    Analytics analytics;

    private GoogleMap map;
    private ClusterManager<ChurchClusterItem> clusterManager;
    private ChurchViewHolder churchViewHolder;
    private List<Integer> favoriteChuchIds = new ArrayList<>();
    private ChurchWithMasses selectedChurch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        ButterKnife.bind(this, view);
        churchViewHolder = new ChurchViewHolder(churchCardContainer, this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        churchesMapViewModel.getChurcesLiveData().observe(this, this::onChurchesLoaded);
        churchesMapViewModel.getFavorites().observe(this, this::onFavoritesLoaded);
        locationManager.registerListener(this);
    }

    private void onFavoritesLoaded(List<Integer> integers) {
        favoriteChuchIds = integers;
        updateCard();
    }

    @Override
    public void onResume() {
        super.onResume();
        analytics.setCurrentScreen(getActivity(), Analytics.ScreenNames.MAP);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        locationManager.unregisterListener(this);
    }


    private void onChurchesLoaded(List<Church> churches) {
        if (map != null) {
            addPins(churches);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        setupClustering();
        if (churchesMapViewModel.getChurcesLiveData().getValue() != null) {
            addPins(churchesMapViewModel.getChurcesLiveData().getValue());
        }
        if (locationManager.hasPermission()) {
            locationManager.getLastKnownLocation(false);
            map.setMyLocationEnabled(true);
        }
    }

    private void setupClustering() {
        clusterManager = new ClusterManager<>(getActivity(), map);
        clusterManager.setCallbacks(this);
        DefaultIconGenerator<ChurchClusterItem> generator = new DefaultIconGenerator<>(getActivity());
        IconStyle iconStyle = new IconStyle.Builder(getActivity())
                .setClusterBackgroundColor(getResources().getColor(R.color.colorPrimary))
                .setClusterIconResId(R.drawable.map_pin).build();
        generator.setIconStyle(iconStyle);
        clusterManager.setIconGenerator(generator);
        map.setOnCameraIdleListener(clusterManager);
    }

    private void addPins(List<Church> churches) {
        List<ChurchClusterItem> clusterItems = new ArrayList<>();
        for (Church church : churches) {
            clusterItems.add(new ChurchClusterItem(church));
        }
        clusterManager.setItems(clusterItems);
    }

    @Override
    public boolean onClusterClick(@NonNull Cluster<ChurchClusterItem> cluster) {
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (ChurchClusterItem item : cluster.getItems()) {
            builder.include(new LatLng(item.getLatitude(), item.getLongitude()));
        }
        final LatLngBounds bounds = builder.build();
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        return true;
    }

    @Override
    public boolean onClusterItemClick(@NonNull ChurchClusterItem clusterItem) {
        int offset = (getView().getHeight() - churchCardContainer.getTop()) / 2;
        churchesMapViewModel.selectChurch(clusterItem.getChurch().getId()).observe(this, this::onSelectedChurchChanged);
        Point mappoint = map.getProjection().toScreenLocation(new LatLng(clusterItem.getLatitude(), clusterItem.getLongitude()));
        mappoint.set(mappoint.x, mappoint.y + offset);
        map.animateCamera(CameraUpdateFactory.newLatLng(map.getProjection().fromScreenLocation(mappoint)));
        return true;
    }

    private void onSelectedChurchChanged(ChurchWithMasses churchWithMasses) {
        selectedChurch = churchWithMasses;
        updateCard();
    }

    private void updateCard() {
        if (selectedChurch != null) {
            churchCardContainer.setVisibility(View.VISIBLE);
            List<Mass> todaysMasses = MassFilter.filterForDay(selectedChurch.getMasses(), LocalDate.now());
            churchViewHolder.bindTo(selectedChurch.getChurch(), todaysMasses, favoriteChuchIds.contains(selectedChurch.getChurch().getId()));
        } else {
            churchCardContainer.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLocationRetrieved(Location location) {
        LatLng redmond = new LatLng(location.getLatitude(), location.getLongitude());
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(redmond, CURRENT_LOCATION_ZOOM));
    }

    @Override
    public void onLocationError(LocationManager.LocationError error) {

    }

    @Override
    public void onChurchClicked(Church church) {
        router.showChurchDetails(church);
    }

    @Override
    public void onFavoriteClicked(Church church) {
        churchesMapViewModel.toggleFavorite(church.getId());
    }

    @Override
    public void onMassClicked(Mass mass) {
        MassDetailsDialogFragment.newInstance(mass).show(getChildFragmentManager(), "mass_details");
    }
}
