package com.frama.miserend.hu.home.pages.churches;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frama.miserend.hu.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by maczak on 2018. 02. 14..
 */

public class ChurchesFragment extends Fragment {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_churches, container, false);
        ButterKnife.bind(this, v);
        viewPager.setAdapter(new ChurchesPagerAdapter(getChildFragmentManager()));
        return v;
    }
}
