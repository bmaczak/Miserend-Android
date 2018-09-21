package com.frama.miserend.hu.home.pages.churches.view;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses;

/**
 * Created by Balazs on 2018. 02. 12..
 */

public class ChurchDiffCallback extends DiffUtil.ItemCallback<ChurchWithMasses> {

    @Override
    public boolean areItemsTheSame(@NonNull ChurchWithMasses oldChurch, @NonNull ChurchWithMasses newChurch) {
        return oldChurch.getChurch().getId() == newChurch.getChurch().getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull ChurchWithMasses oldChurch, @NonNull ChurchWithMasses newChurch) {
        return oldChurch.equals(newChurch);
    }
}