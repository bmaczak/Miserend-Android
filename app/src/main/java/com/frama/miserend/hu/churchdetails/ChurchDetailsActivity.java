package com.frama.miserend.hu.churchdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.frama.miserend.hu.R;
import com.frama.miserend.hu.base.BaseActivity;
import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses;
import com.frama.miserend.hu.map.StaticMapHelper;
import com.frama.miserend.hu.utils.ViewUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Balazs on 2018. 02. 18..
 */

public class ChurchDetailsActivity extends BaseActivity {

    @BindView(R.id.church_header_image)
    SimpleDraweeView headerImage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.church_name)
    TextView churchName;
    @BindView(R.id.church_common_name)
    TextView churchCommonName;
    @BindView(R.id.static_map)
    SimpleDraweeView staticMap;
    @BindView(R.id.church_address)
    TextView churchAddress;
    @BindView(R.id.church_getting_there)
    TextView churchGettingThere;

    @Inject
    ChurchDetailsViewModel churchDetailsViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_church_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        churchDetailsViewModel.getChurchWithMasses().observe(this, this::onChurchDetailsLoaded);
    }

    private void onChurchDetailsLoaded(ChurchWithMasses churchWithMasses) {
        Church church = churchWithMasses.getChurch();
        headerImage.setImageURI(churchWithMasses.getChurch().getImageUrl());
        ViewUtils.setTextOrHide(churchName, church.getName());
        ViewUtils.setTextOrHide(churchCommonName, church.getCommonName());
        staticMap.setImageURI(StaticMapHelper.getSaticMapUrl(this, churchWithMasses.getChurch().getLat(), churchWithMasses.getChurch().getLon(), staticMap.getWidth(), staticMap.getHeight()));
        ViewUtils.setTextOrHide(churchAddress, church.getAddress());
        ViewUtils.setTextOrHide(churchGettingThere, church.getGettingThere());
    }
}
