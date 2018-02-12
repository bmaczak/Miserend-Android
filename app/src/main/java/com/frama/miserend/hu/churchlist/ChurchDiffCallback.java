package com.frama.miserend.hu.churchlist;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;

import com.frama.miserend.hu.database.relations.ChurchWithMasses;

/**
 * Created by Balazs on 2018. 02. 12..
 */

public class ChurchDiffCallback extends DiffCallback<ChurchWithMasses> {

    @Override
    public boolean areItemsTheSame(@NonNull ChurchWithMasses oldChurch, @NonNull ChurchWithMasses newChurch) {
        return oldChurch.getChurch().getTid() == newChurch.getChurch().getTid();
    }

    @Override
    public boolean areContentsTheSame(@NonNull ChurchWithMasses oldChurch, @NonNull ChurchWithMasses newChurch) {
        return oldChurch.equals(newChurch);
    }
}