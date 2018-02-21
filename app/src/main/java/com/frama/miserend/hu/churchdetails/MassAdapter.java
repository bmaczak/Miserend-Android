package com.frama.miserend.hu.churchdetails;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.frama.miserend.hu.R;

import java.util.List;

/**
 * Created by Balazs on 2018. 02. 21..
 */

public class MassAdapter extends RecyclerView.Adapter<MassListViewHolder> {

    private List<DayOfMasses> masses;

    public MassAdapter(List<DayOfMasses> masses) {
        this.masses = masses;
    }

    @Override
    public MassListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MassListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day_of_masses, parent, false));
    }

    @Override
    public void onBindViewHolder(MassListViewHolder holder, int position) {
        holder.bind(masses.get(position));
    }

    @Override
    public int getItemCount() {
        return masses.size();
    }
}
