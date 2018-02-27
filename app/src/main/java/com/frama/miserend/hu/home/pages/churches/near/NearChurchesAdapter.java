package com.frama.miserend.hu.home.pages.churches.near;

import android.arch.paging.PagedListAdapter;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses;
import com.frama.miserend.hu.home.pages.churches.ChurchDiffCallback;
import com.frama.miserend.hu.home.pages.churches.ChurchViewHolder;
import com.frama.miserend.hu.home.pages.churches.filter.MassFilter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Balazs on 2018. 02. 11..
 */

public class NearChurchesAdapter extends PagedListAdapter<ChurchWithMasses, ChurchViewHolder> {

    private Location currentLocation;

    private List<Integer> favorites;

    private ChurchViewHolder.ChurchListActionListener churchListActionListener;

    public NearChurchesAdapter(ChurchViewHolder.ChurchListActionListener churchListActionListener) {
        super(new ChurchDiffCallback());
        this.churchListActionListener = churchListActionListener;
        favorites = new ArrayList<>();
    }

    @Override
    public ChurchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chuch, parent, false);
        return new ChurchViewHolder(view, churchListActionListener);
    }

    @Override
    public void onBindViewHolder(ChurchViewHolder holder, int position) {
        ChurchWithMasses churchWithMasses = getItem(position);
        if (churchWithMasses != null) {
            holder.bindTo(churchWithMasses.getChurch(), MassFilter.filterForDay(churchWithMasses.getMasses(), Calendar.getInstance()),
                    currentLocation, favorites.contains(churchWithMasses.getChurch().getId()));
        } else {
            holder.clear();
        }
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void setFavorites(List<Integer> favorites) {
        this.favorites = favorites;
        notifyDataSetChanged();
    }
}
