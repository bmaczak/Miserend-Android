package com.frama.miserend.hu.database.miserend.relations;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.database.miserend.entities.Image;
import com.frama.miserend.hu.database.miserend.entities.Mass;

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

    @Relation(parentColumn = "tid",
            entityColumn = "tid")
    private List<Image> images;

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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
