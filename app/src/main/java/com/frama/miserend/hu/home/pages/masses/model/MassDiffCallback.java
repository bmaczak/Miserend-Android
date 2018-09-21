package com.frama.miserend.hu.home.pages.masses.model;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.frama.miserend.hu.database.miserend.relations.MassWithChurch;

/**
 * Created by Balazs on 2018. 02. 12..
 */

public class MassDiffCallback extends DiffUtil.ItemCallback<MassWithChurch> {

    @Override
    public boolean areItemsTheSame(@NonNull MassWithChurch oldMass, @NonNull MassWithChurch newMass) {
        return oldMass.getChurch().getId() == newMass.getChurch().getId()
                && oldMass.getMass().getTime().equals(newMass.getMass().getTime());
    }

    @Override
    public boolean areContentsTheSame(@NonNull MassWithChurch oldMass, @NonNull MassWithChurch newMass) {
        return oldMass.equals(newMass);
    }
}