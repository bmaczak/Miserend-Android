package com.frama.miserend.hu.home.pages.masses;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;

import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses;
import com.frama.miserend.hu.database.miserend.relations.MassWithChuch;

/**
 * Created by Balazs on 2018. 02. 12..
 */

public class MassDiffCallback extends DiffCallback<MassWithChuch> {

    @Override
    public boolean areItemsTheSame(@NonNull MassWithChuch oldMass, @NonNull MassWithChuch newMass) {
        return oldMass.getChurch().getId() == newMass.getChurch().getId()
                && oldMass.getMass().getTime().equals(newMass.getMass().getTime());
    }

    @Override
    public boolean areContentsTheSame(@NonNull MassWithChuch oldMass, @NonNull MassWithChuch newMass) {
        return oldMass.equals(newMass);
    }
}