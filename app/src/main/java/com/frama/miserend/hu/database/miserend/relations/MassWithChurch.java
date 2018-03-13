package com.frama.miserend.hu.database.miserend.relations;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.database.miserend.entities.Mass;

import java.util.List;

/**
 * Created by maczak on 2018. 03. 12..
 */

public class MassWithChurch {
    @Embedded
    private Mass mass;

    @Relation(parentColumn = "tid",
            entityColumn = "tid")
    private List<Church> churches;

    public Church getChurch() {
        return churches != null && !churches.isEmpty() ? churches.get(0) : null;
    }

    public List<Church> getChurches() {
        return churches;
    }

    public void setChurches(List<Church> churches) {
        this.churches = churches;
    }

    public Mass getMass() {
        return mass;
    }

    public void setMass(Mass mass) {
        this.mass = mass;
    }
}
