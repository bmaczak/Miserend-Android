package com.frama.miserend.hu.home.pages.churches;

import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.frama.miserend.hu.R;
import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.database.miserend.entities.Mass;
import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses;
import com.frama.miserend.hu.utils.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.church_distance)
    TextView distanceText;
    @BindView(R.id.church_thumb)
    SimpleDraweeView churchThumbnail;
    @BindView(R.id.church_action_favorite)
    ImageView favoriteImage;

    private int dayOfWeekToday;
    private ChurchListActionListener churchListActionListener;

    private ChurchWithMasses churchWithMasses;

    public ChurchViewHolder(View itemView, int dayOfWeekToday, ChurchListActionListener churchListActionListener) {
        super(itemView);
        this.dayOfWeekToday = dayOfWeekToday;
        this.churchListActionListener = churchListActionListener;
        ButterKnife.bind(this, itemView);
    }

    public void bindTo(ChurchWithMasses churchWithMasses, boolean isFavorite) {
        bindTo(churchWithMasses, null, isFavorite);
    }

    public void bindTo(ChurchWithMasses churchWithMasses, Location currentLocation, boolean isFavorite) {
        this.churchWithMasses = churchWithMasses;
        churchName.setText(churchWithMasses.getChurch().getName());
        churchCommonName.setText(churchWithMasses.getChurch().getCommonName());
        churchThumbnail.setImageURI(churchWithMasses.getChurch().getImageUrl());
        massesText.setText(getMassesText(churchWithMasses));
        if (currentLocation != null) {
            distanceText.setVisibility(View.VISIBLE);
            distanceText.setText(getDistanceText(getDistance(currentLocation, churchWithMasses.getChurch())));
        } else {
            distanceText.setVisibility(View.GONE);
        }
        favoriteImage.setImageResource(isFavorite ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
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

    private float getDistance(Location location, Church church) {
        Location locationA = new Location("point A");
        locationA.setLatitude(church.getLat());
        locationA.setLongitude(church.getLon());
        return location.distanceTo(locationA);
    }


    public String getDistanceText(float distance) {
        if (distance < 1000) {
            return ((int) distance) + " m";
        } else {
            return String.format("%.2f km", (distance / 1000));
        }
    }

    @OnClick(R.id.church_action_favorite)
    void onFaviriteClicked() {
        churchListActionListener.onFavoriteClicked(churchWithMasses.getChurch());
    }

    public interface ChurchListActionListener {
        void onChurchClicked(Church church);

        void onFavoriteClicked(Church church);
    }
}
