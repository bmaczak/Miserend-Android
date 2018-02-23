package com.frama.miserend.hu.churchdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.frama.miserend.hu.R;
import com.frama.miserend.hu.base.BaseActivity;
import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses;
import com.frama.miserend.hu.home.pages.churches.filter.MassFilter;
import com.frama.miserend.hu.map.StaticMapHelper;
import com.frama.miserend.hu.utils.ViewUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    @BindView(R.id.no_masses_text)
    TextView noMassesText;
    @BindView(R.id.masses_recycler_view)
    RecyclerView massesRecyclerView;

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
        displayMasses(churchWithMasses);
    }

    private void displayMasses(ChurchWithMasses churchWithMasses) {
        if (!churchWithMasses.getMasses().isEmpty()) {
            List<DayOfMasses> dayOfMassesList = new ArrayList<>();
            for (int i = 0; i < 20; ++i) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, i);
                DayOfMasses dayOfMasses = new DayOfMasses(calendar, MassFilter.filterForDay(churchWithMasses.getMasses(), calendar));
                dayOfMassesList.add(dayOfMasses);
            }
            massesRecyclerView.setAdapter(new MassAdapter(dayOfMassesList));
        } else {
            massesRecyclerView.setVisibility(View.GONE);
            noMassesText.setVisibility(View.VISIBLE);
        }
    }
}
