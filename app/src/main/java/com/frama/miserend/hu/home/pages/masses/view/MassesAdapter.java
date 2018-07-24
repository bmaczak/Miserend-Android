package com.frama.miserend.hu.home.pages.masses.view;

import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.frama.miserend.hu.R;
import com.frama.miserend.hu.database.miserend.relations.MassWithChurch;
import com.frama.miserend.hu.utils.ChurchUtils;
import com.frama.miserend.hu.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by maczak on 2018. 03. 12..
 */

public class MassesAdapter extends RecyclerView.Adapter<MassesAdapter.MassViewHolder> {

    private List<MassWithChurch> masses;
    private Location currentLocation;
    private MassViewHolder.MassListActionListener actionListener;

    public MassesAdapter(MassViewHolder.MassListActionListener actionListener) {
        this.actionListener = actionListener;
        masses = new ArrayList<>();
    }

    @Override
    public MassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mass, parent, false);
        return new MassViewHolder(view, actionListener);
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

    public void update(List<MassWithChurch> masses) {
        this.masses.clear();
        this.masses.addAll(masses);
        notifyDataSetChanged();
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
        notifyDataSetChanged();
    }

    private MassWithChurch getItem(int position) {
        return masses.get(position);
    }

    public static class MassViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.church_thumb)
        SimpleDraweeView churchThumb;
        @BindView(R.id.church_name)
        TextView churchName;
        @BindView(R.id.mass_time)
        TextView massTime;
        @BindView(R.id.church_distance)
        TextView distance;

        private MassWithChurch mass;
        private MassListActionListener actionListener;

        public MassViewHolder(View itemView, MassListActionListener actionListener) {
            super(itemView);
            this.actionListener = actionListener;
            ButterKnife.bind(this, itemView);
        }

        public void bind(MassWithChurch massWithChurch, Location location) {
            mass = massWithChurch;
            churchThumb.setImageURI(massWithChurch.getChurch().getImageUrl());
            churchName.setText(massWithChurch.getChurch().getName());
            massTime.setText(DateUtils.cutSecondsFromTime(massWithChurch.getMass().getTime()));
            if (location != null) {
                distance.setVisibility(View.VISIBLE);
                distance.setText(getDistanceText(ChurchUtils.distanceTo(location, massWithChurch.getChurch())));
            } else {
                distance.setVisibility(View.GONE);
            }
        }

        public String getDistanceText(float distance) {
            if (distance < 1000) {
                return ((int) distance) + " m";
            } else {
                return String.format("%.2f km", (distance / 1000));
            }
        }

        @OnClick(R.id.mass_item_root)
        public void onMassClicked() {
            actionListener.onMassListItemClicked(mass);
        }

        public interface MassListActionListener {
            void onMassListItemClicked(MassWithChurch massWithChurch);
        }
    }
}
