package com.frama.miserend.hu.search.result.church.view;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses;
import com.frama.miserend.hu.home.pages.churches.filter.MassFilter;
import com.frama.miserend.hu.home.pages.churches.view.ChurchDiffUtilCallback;
import com.frama.miserend.hu.home.pages.churches.view.ChurchViewHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Balazs on 2018. 02. 17..
 */

public class ChurchSearchResultAdapter extends RecyclerView.Adapter<ChurchViewHolder> {

    private List<ChurchWithMasses> churches;
    private List<Integer> favorites;
    private ChurchViewHolder.ChurchListActionListener churchListActionListener;

    public ChurchSearchResultAdapter(ChurchViewHolder.ChurchListActionListener churchListActionListener) {
        super();
        this.churchListActionListener = churchListActionListener;
        churches = new ArrayList<>();
    }

    @Override
    public ChurchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chuch, parent, false);
        return new ChurchViewHolder(view, churchListActionListener);
    }

    @Override
    public void onBindViewHolder(ChurchViewHolder holder, int position) {
        ChurchWithMasses churchWithMasses = churches.get(position);
        if (churchWithMasses != null) {
            boolean isFavorite = favorites != null && favorites.contains(churchWithMasses.getChurch().getId());
            holder.bindTo(churchWithMasses.getChurch(), MassFilter.filterForDay(churchWithMasses.getMasses(), Calendar.getInstance()), isFavorite);
        } else {
            holder.clear();
        }
    }

    public void update(List<ChurchWithMasses> favoriteChurches) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ChurchDiffUtilCallback(this.churches, favoriteChurches));
        diffResult.dispatchUpdatesTo(this);
        this.churches = favoriteChurches;
    }

    public void setFavorites(List<Integer> favorites) {
        this.favorites = favorites;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return churches.size();
    }
}
