package com.frama.miserend.hu.churchlist;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.database.relations.ChurchWithMasses;

import java.util.Calendar;

/**
 * Created by Balazs on 2018. 02. 11..
 */

public class NearChurchesAdapter extends PagedListAdapter<ChurchWithMasses, ChurchViewHolder> {

    private int dayOfWeekToday;

    public NearChurchesAdapter() {
        super(DIFF_CALLBACK);
        dayOfWeekToday = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    @Override
    public ChurchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChurchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chuch, parent, false), dayOfWeekToday);
    }

    @Override
    public void onBindViewHolder(ChurchViewHolder holder, int position) {
        ChurchWithMasses user = getItem(position);
        if (user != null) {
            holder.bindTo(user);
        } else {
            holder.clear();
        }
    }

    public static final DiffCallback<ChurchWithMasses> DIFF_CALLBACK = new DiffCallback<ChurchWithMasses>() {
        @Override
        public boolean areItemsTheSame(@NonNull ChurchWithMasses oldUser, @NonNull ChurchWithMasses newUser) {
            // User properties may have changed if reloaded from the DB, but ID is fixed
            return oldUser.getChurch().getTid() == newUser.getChurch().getTid();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ChurchWithMasses oldUser, @NonNull ChurchWithMasses newUser) {
            // NOTE: if you use equals, your object must properly override Object#equals()
            // Incorrectly returning false here will result in too many animations.
            return oldUser.equals(newUser);
        }
    };


}
