package com.frama.miserend.hu.home.pages.churches;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.frama.miserend.hu.home.pages.churches.near.NearChurchesFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maczak on 2018. 02. 14..
 */

public class ChurchesPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> fragments;

    public ChurchesPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        fragments.add(new NearChurchesFragment());
        fragments.add(new NearChurchesFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position == 0 ? "Közelben" : "Kedvencek";
    }
}
