package com.frama.miserend.hu.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.database.manager.DatabaseState;
import com.frama.miserend.hu.di.components.HomeScreenComponent;
import com.frama.miserend.hu.home.pages.churches.ChurchesFragment;
import com.frama.miserend.hu.home.pages.churches.near.NearChurchesFragment;
import com.frama.miserend.hu.home.pages.map.ChurchesMapFragment;
import com.frama.miserend.hu.home.pages.masses.MassesFragment;
import com.frama.miserend.hu.view.BottomNavigationViewBehavior;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Balazs on 2018. 02. 10..
 */

public class HomeScreenActivity extends AppCompatActivity {

    @Inject
    HomeViewModel viewModel;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeScreenComponent.Injector.inject(this);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        viewModel.getDatabaseState().observe(this, this::onDatabaseStateChanged);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
    }

    private void onDatabaseStateChanged(DatabaseState databaseState) {
        switch (databaseState) {
            case UP_TO_DATE:
                showFragment(new ChurchesFragment());
                break;
            case NOT_FOUND:
                viewModel.downloadDatabase();
                break;
        }
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_churches:
                showFragment(new ChurchesFragment());
                return true;
            case R.id.action_masses:
                showFragment(new MassesFragment());
                return true;
            case R.id.action_map:
                showFragment(new ChurchesMapFragment());
                return true;
            default:
                return false;
        }
    }
}