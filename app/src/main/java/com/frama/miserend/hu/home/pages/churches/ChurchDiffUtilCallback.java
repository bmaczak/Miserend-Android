package com.frama.miserend.hu.home.pages.churches;

import android.support.v7.util.DiffUtil;

import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses;

import java.util.List;

/**
 * Created by Balazs on 2018. 02. 17..
 */

public class ChurchDiffUtilCallback extends DiffUtil.Callback {

    private final List<ChurchWithMasses> oldList;
    private final List<ChurchWithMasses> newList;

    public ChurchDiffUtilCallback(List<ChurchWithMasses> oldList, List<ChurchWithMasses> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getChurch().getId() == newList.get(newItemPosition).getChurch().getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
