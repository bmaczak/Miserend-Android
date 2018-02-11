package com.frama.miserend.hu.churchlist;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.frama.miserend.hu.R;
import com.frama.miserend.hu.database.entities.Mass;
import com.frama.miserend.hu.database.relations.ChurchWithMasses;
import com.frama.miserend.hu.utils.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Balazs on 2018. 02. 11..
 */

public class ChurchViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.church_name)
    TextView churchName;
    @BindView(R.id.church_common_name)
    TextView churchCommonName;
    @BindView(R.id.masses_text)
    TextView massesText;
    @BindView(R.id.church_thumb)
    SimpleDraweeView churchThumbnail;

    private int dayOfWeekToday;

    public ChurchViewHolder(View itemView, int dayOfWeekToday) {
        super(itemView);
        this.dayOfWeekToday = dayOfWeekToday;
        ButterKnife.bind(this, itemView);
    }

    public void bindTo(ChurchWithMasses churchWithMasses) {
        churchName.setText(churchWithMasses.getChurch().getName());
        churchCommonName.setText(churchWithMasses.getChurch().getCommonName());
        churchThumbnail.setImageURI(churchWithMasses.getChurch().getImageUrl());
        massesText.setText(getMassesText(churchWithMasses));
    }

    public void clear() {
        churchName.setText("");
        churchThumbnail.setImageURI("");
    }

    private String getMassesText(ChurchWithMasses church) {
        Context context = churchName.getContext();
        Resources res = context.getResources();
        String masses = "";
        for (Mass mass : church.getMasses()) {
            if (mass.getDay() == dayOfWeekToday) {
                masses += mass.getTime() + " ";
            }
        }
        if (masses.length() == 0) {
            masses = "-";
        }
        return String.format(res.getString(R.string.masses_text), DateUtils.getNameOfDay(context, dayOfWeekToday), masses);
    }
}
