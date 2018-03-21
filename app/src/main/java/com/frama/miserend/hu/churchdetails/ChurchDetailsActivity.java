package com.frama.miserend.hu.churchdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.frama.miserend.hu.R;
import com.frama.miserend.hu.base.BaseActivity;
import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.database.miserend.entities.Mass;
import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses;
import com.frama.miserend.hu.home.pages.churches.filter.MassFilter;
import com.frama.miserend.hu.map.StaticMapHelper;
import com.frama.miserend.hu.report.ReportDialogFragment;
import com.frama.miserend.hu.utils.ViewUtils;
import com.google.android.flexbox.FlexboxLayout;
import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Balazs on 2018. 02. 18..
 */

public class ChurchDetailsActivity extends BaseActivity implements OnMassClickedListener {

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
    @BindView(R.id.masses_today_flexbox)
    FlexboxLayout massesTodayFlexbox;
    @BindView(R.id.masses_this_sunday_flexbox)
    FlexboxLayout massesThisSundayFlexbox;
    @BindView(R.id.no_masses_text)
    TextView noMassesText;
    @BindView(R.id.masses_recycler_view)
    RecyclerView massesRecyclerView;
    @BindView(R.id.images_pager)
    ViewPager imagesPager;
    @BindView(R.id.images_pager_indicator)
    PageIndicatorView imagesPagerIndicator;

    @Inject
    ChurchDetailsViewModel churchDetailsViewModel;

    private GalleryPagerAdapter imagesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_church_details);
        ButterKnife.bind(this);
        setupActionBar();
        setupGallery();
        churchDetailsViewModel.getChurchWithMasses().observe(this, this::onChurchDetailsLoaded);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.church_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.report_error:
                ReportDialogFragment.newInstance(churchDetailsViewModel.getChurchId()).show(getSupportFragmentManager(), "report");
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
    }

    private void setupGallery() {
        imagesAdapter = new GalleryPagerAdapter();
        imagesPager.setAdapter(imagesAdapter);
        imagesPagerIndicator.setViewPager(imagesPager);
        imagesPagerIndicator.setDynamicCount(true);
        imagesPagerIndicator.setRadius(4);
    }

    private void onChurchDetailsLoaded(ChurchWithMasses churchWithMasses) {
        Church church = churchWithMasses.getChurch();
        ViewUtils.setTextOrHide(churchName, church.getName());
        ViewUtils.setTextOrHide(churchCommonName, church.getCommonName());
        staticMap.setImageURI(StaticMapHelper.getSaticMapUrl(this, churchWithMasses.getChurch().getLat(), churchWithMasses.getChurch().getLon(), staticMap.getWidth(), staticMap.getHeight()));
        ViewUtils.setTextOrHide(churchAddress, church.getAddress());
        ViewUtils.setTextOrHide(churchGettingThere, church.getGettingThere());
        displayMasses(churchWithMasses);
        imagesAdapter.setImages(churchWithMasses.getImages());
    }

    private void displayMasses(ChurchWithMasses churchWithMasses) {
        if (!churchWithMasses.getMasses().isEmpty()) {
            displayHighlightedMasses(churchWithMasses);
            List<DayOfMasses> dayOfMassesList = new ArrayList<>();
            for (int i = 1; i < 20; ++i) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, i);
                DayOfMasses dayOfMasses = new DayOfMasses(calendar, MassFilter.filterForDay(churchWithMasses.getMasses(), calendar));
                dayOfMassesList.add(dayOfMasses);
            }
            MassAdapter adapter = new MassAdapter(dayOfMassesList);
            adapter.setOnMassClickedListener(this);
            massesRecyclerView.setAdapter(adapter);
        } else {
            massesRecyclerView.setVisibility(View.GONE);
            noMassesText.setVisibility(View.VISIBLE);
        }
    }

    private void displayHighlightedMasses(ChurchWithMasses churchWithMasses) {

        Calendar today = Calendar.getInstance();
        List<Mass> todaysMasses = MassFilter.filterForDay(churchWithMasses.getMasses(), today);
        addMassesToFlexboxLayout(massesTodayFlexbox, todaysMasses);

        Calendar sunday = Calendar.getInstance();
        sunday.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        List<Mass> sundayMasses = MassFilter.filterForDay(churchWithMasses.getMasses(), sunday);
        addMassesToFlexboxLayout(massesThisSundayFlexbox, sundayMasses);
    }

    private void addMassesToFlexboxLayout(FlexboxLayout flexboxLayout, List<Mass> masses) {
        LayoutInflater layoutInflater = getLayoutInflater();
        for (Mass mass : masses) {
            View view = ViewUtils.createMassFlexboxItem(layoutInflater, flexboxLayout, mass);
            view.setOnClickListener(view1 -> showMassDetailsDialog(mass));
            flexboxLayout.addView(view);
        }
    }

    @Override
    public void onMassClicked(Mass mass) {
        showMassDetailsDialog(mass);
    }

    private void showMassDetailsDialog(Mass mass) {
        MassDetailsDialogFragment.newInstance(mass).show(getSupportFragmentManager(), "mass_details");
    }
}
