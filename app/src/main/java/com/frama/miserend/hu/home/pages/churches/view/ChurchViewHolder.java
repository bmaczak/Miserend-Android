package com.frama.miserend.hu.home.pages.churches.view;

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
import com.frama.miserend.hu.utils.ChurchUtils;

import java.util.List;

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

    private ChurchListActionListener churchListActionListener;

    private Church church;

    public ChurchViewHolder(View itemView, ChurchListActionListener churchListActionListener) {
        super(itemView);
        this.churchListActionListener = churchListActionListener;
        ButterKnife.bind(this, itemView);
    }

    public void bindTo(Church church, List<Mass> masses, boolean isFavorite) {
        bindTo(church, masses, null, isFavorite);
    }

    public void bindTo(Church church, List<Mass> masses, Location currentLocation, boolean isFavorite) {
        this.church = church;
        churchName.setText(church.getName());
        churchCommonName.setText(church.getCommonName());
        churchThumbnail.setImageURI(church.getImageUrl());
        massesText.setText(getMassesText(masses));
        if (currentLocation != null) {
            distanceText.setVisibility(View.VISIBLE);
            distanceText.setText(getDistanceText(ChurchUtils.distanceTo(currentLocation, church)));
        } else {
            distanceText.setVisibility(View.GONE);
        }
        favoriteImage.setImageResource(isFavorite ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
    }

    public void clear() {
        churchName.setText("");
        churchThumbnail.setImageURI("");
    }

    private String getMassesText(List<Mass> masses) {
        Context context = churchName.getContext();
        Resources res = context.getResources();
        String massesText = "";
        for (Mass mass : masses) {
            massesText += mass.getTime() + " ";
        }
        if (massesText.length() == 0) {
            massesText = "-";
        }
        return String.format(res.getString(R.string.masses_text), massesText);
    }


    public String getDistanceText(float distance) {
        if (distance < 1000) {
            return ((int) distance) + " m";
        } else {
            return String.format("%.2f km", (distance / 1000));
        }
    }

    @OnClick(R.id.church_action_favorite)
    void onFavoriteIconClicked() {
        churchListActionListener.onFavoriteClicked(church);
    }

    @OnClick(R.id.church_card)
    void onChurcClicked() {
        churchListActionListener.onChurchClicked(church);
    }

    public interface ChurchListActionListener {
        void onChurchClicked(Church church);

        void onFavoriteClicked(Church church);
    }
}
