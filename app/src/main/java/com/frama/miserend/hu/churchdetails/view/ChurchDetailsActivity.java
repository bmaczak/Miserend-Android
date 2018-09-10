package com.frama.miserend.hu.churchdetails.view;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.frama.miserend.hu.R;
import com.frama.miserend.hu.base.FragmentHostActivity;
import com.frama.miserend.hu.churchdetails.viewmodel.ChurchDetailsViewModel;
import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.database.miserend.entities.Mass;
import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses;
import com.frama.miserend.hu.firebase.Analytics;
import com.frama.miserend.hu.home.pages.churches.filter.MassFilter;
import com.frama.miserend.hu.map.StaticMapHelper;
import com.frama.miserend.hu.massdetails.view.MassDetailsDialogFragment;
import com.frama.miserend.hu.report.view.ReportDialogFragment;
import com.frama.miserend.hu.router.Router;
import com.frama.miserend.hu.utils.ViewUtils;
import com.google.android.flexbox.FlexboxLayout;
import com.rd.PageIndicatorView;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Balazs on 2018. 02. 18..
 */

public class ChurchDetailsActivity extends FragmentHostActivity implements OnMassClickedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.church_name)
    TextView churchName;
    @BindView(R.id.favorite_icon)
    ImageView favoriteIcon;
    @BindView(R.id.favorite_label)
    TextView favoriteLabel;
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
    @Inject
    Router router;
    @Inject
    Analytics analytics;

    private GalleryPagerAdapter imagesAdapter;

    private ChurchWithMasses churchWithMasses;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_church_details);
        ButterKnife.bind(this);
        setupActionBar();
        setupGallery();
        churchDetailsViewModel.getChurchWithMasses().observe(this, this::onChurchDetailsLoaded);
        churchDetailsViewModel.isFavorite().observe(this, this::onFavoriteChanged);
    }

    @Override
    protected void onResume() {
        super.onResume();
        analytics.setCurrentScreen(this, Analytics.ScreenNames.CHURCH_DETAILS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
        this.churchWithMasses = churchWithMasses;
        Church church = churchWithMasses.getChurch();
        ViewUtils.setTextOrHide(churchName, church.getName());
        ViewUtils.setTextOrHide(churchCommonName, church.getCommonName());
        staticMap.setImageURI(StaticMapHelper.getSaticMapUrl(this, churchWithMasses.getChurch().getLat(), churchWithMasses.getChurch().getLon(), staticMap.getWidth(), staticMap.getHeight()));
        ViewUtils.setHtmlTextOrHide(churchAddress, church.getAddress());
        ViewUtils.setHtmlTextOrHide(churchGettingThere, church.getGettingThere());
        displayMasses();
        imagesAdapter.setImages(churchWithMasses.getImages());
    }

    private void onFavoriteChanged(Boolean favorite) {
        favoriteIcon.setImageResource(favorite ? R.drawable.ic_favorite_big : R.drawable.ic_favorite_border_big);
        favoriteLabel.setText(favorite ? R.string.remove_from_favorites : R.string.add_to_favorites);
    }

    private void displayMasses() {
        if (!churchWithMasses.getMasses().isEmpty()) {
            displayHighlightedMasses();
            List<DayOfMasses> dayOfMassesList = new ArrayList<>();
            for (int i = 1; i < 20; ++i) {
                LocalDate day = LocalDate.now().plus(i, ChronoUnit.DAYS);
                DayOfMasses dayOfMasses = new DayOfMasses(day, MassFilter.filterForDay(churchWithMasses.getMasses(), day));
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

    private void displayHighlightedMasses() {

        List<Mass> todaysMasses = MassFilter.filterForDay(churchWithMasses.getMasses(), LocalDate.now());
        addMassesToFlexboxLayout(massesTodayFlexbox, todaysMasses);

        List<Mass> sundayMasses = MassFilter.filterForDay(churchWithMasses.getMasses(), LocalDate.now().with(DayOfWeek.SUNDAY));
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

    @OnClick(R.id.btn_favorite)
    public void toggleFavorite() {
        churchDetailsViewModel.toggleFavorite();
    }

    @OnClick(R.id.btn_report)
    public void report() {
        ReportDialogFragment.newInstance(churchDetailsViewModel.getChurchId()).show(getSupportFragmentManager(), "report");
    }

    @OnClick(R.id.btn_navigate)
    public void navigate() {
        router.startGoogleNavigation(churchWithMasses.getChurch());
    }

    @OnClick(R.id.static_map)
    public void showOnMap() {
        router.showOnMap(churchWithMasses.getChurch());
    }
}
