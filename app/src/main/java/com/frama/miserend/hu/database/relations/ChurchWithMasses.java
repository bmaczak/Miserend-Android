package com.frama.miserend.hu.database.relations;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import com.frama.miserend.hu.database.entities.Church;
import com.frama.miserend.hu.database.entities.Mass;

import java.util.List;

/**
 * Created by Balazs on 2018. 02. 11..
 */

public class ChurchWithMasses {
    @Embedded
    private Church church;

    @Relation(parentColumn = "tid",
            entityColumn = "tid")
    private List<Mass> masses;

    public Church getChurch() {
        return church;
    }

    public void setChurch(Church church) {
        this.church = church;
    }

    public List<Mass> getMasses() {
        return masses;
    }

    public void setMasses(List<Mass> masses) {
        this.masses = masses;
    }
}
