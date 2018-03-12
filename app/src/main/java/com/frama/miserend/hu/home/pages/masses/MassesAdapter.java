package com.frama.miserend.hu.home.pages.masses;

import android.arch.paging.PagedListAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.frama.miserend.hu.R;
import com.frama.miserend.hu.database.miserend.relations.MassWithChuch;
import com.frama.miserend.hu.utils.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by maczak on 2018. 03. 12..
 */

public class MassesAdapter extends PagedListAdapter<MassWithChuch, MassesAdapter.MassViewHolder> {

    public MassesAdapter() {
        super(new MassDiffCallback());
    }

    @Override
    public MassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MassViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mass, parent, false));
    }

    @Override
    public void onBindViewHolder(MassViewHolder holder, int position) {
        if (getItem(position) != null) {
            holder.bind(getItem(position));
        }
    }

    public class MassViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.church_thumb)
        SimpleDraweeView churchThumb;
        @BindView(R.id.church_name)
        TextView churchName;
        @BindView(R.id.mass_time)
        TextView massTime;

        public MassViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(MassWithChuch massWithChuch) {
            churchThumb.setImageURI(massWithChuch.getChurch().getImageUrl());
            churchName.setText(massWithChuch.getChurch().getName());
            massTime.setText(DateUtils.cutSecondsFromTime(massWithChuch.getMass().getTime()));
        }
    }
}
