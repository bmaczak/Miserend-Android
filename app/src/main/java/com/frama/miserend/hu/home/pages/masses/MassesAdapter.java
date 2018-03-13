package com.frama.miserend.hu.home.pages.masses;

import android.arch.paging.PagedListAdapter;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.frama.miserend.hu.R;
import com.frama.miserend.hu.database.miserend.relations.MassWithChuch;
import com.frama.miserend.hu.utils.ChurchUtils;
import com.frama.miserend.hu.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by maczak on 2018. 03. 12..
 */

public class MassesAdapter extends RecyclerView.Adapter<MassesAdapter.MassViewHolder> {

    private List<MassWithChuch> masses;
    private Location currentLocation;

    public MassesAdapter() {
        masses = new ArrayList<>();
    }

    @Override
    public MassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MassViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mass, parent, false));
    }

    @Override
    public void onBindViewHolder(MassViewHolder holder, int position) {
        if (getItem(position) != null) {
            holder.bind(getItem(position), currentLocation);
        }
    }

    @Override
    public int getItemCount() {
        return masses.size();
    }

    public void update(List<MassWithChuch> masses) {
        this.masses.clear();
        this.masses.addAll(masses);
        notifyDataSetChanged();
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
        notifyDataSetChanged();
    }

    private MassWithChuch getItem(int position) {
        return masses.get(position);
    }

    public class MassViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.church_thumb)
        SimpleDraweeView churchThumb;
        @BindView(R.id.church_name)
        TextView churchName;
        @BindView(R.id.mass_time)
        TextView massTime;
        @BindView(R.id.church_distance)
        TextView distance;

        public MassViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(MassWithChuch massWithChuch, Location location) {
            churchThumb.setImageURI(massWithChuch.getChurch().getImageUrl());
            churchName.setText(massWithChuch.getChurch().getName());
            massTime.setText(DateUtils.cutSecondsFromTime(massWithChuch.getMass().getTime()));
            distance.setText(getDistanceText(ChurchUtils.distanceTo(currentLocation, massWithChuch.getChurch())));
        }

        public String getDistanceText(float distance) {
            if (distance < 1000) {
                return ((int) distance) + " m";
            } else {
                return String.format("%.2f km", (distance / 1000));
            }
        }
    }
}
