package com.frama.miserend.hu.home.pages.churches.view;

import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.base.BaseFragment;
import com.frama.miserend.hu.firebase.Analytics;
import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by maczak on 2018. 02. 14..
 */

public class ChurchesFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    @Inject
    Analytics analytics;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_churches, container, false);
        ButterKnife.bind(this, v);
        viewPager.setAdapter(new ChurchesPagerAdapter(getChildFragmentManager()));
        viewPager.addOnPageChangeListener(this);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        analytics.setCurrentScreen(getActivity(), Analytics.ScreenNames.NEAR_CHURCHES);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        analytics.setCurrentScreen(getActivity(), position == 0 ? Analytics.ScreenNames.NEAR_CHURCHES : Analytics.ScreenNames.FAVORITE_CHURCHES);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
