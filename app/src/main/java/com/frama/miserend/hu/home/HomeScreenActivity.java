package com.frama.miserend.hu.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.home.pages.churches.near.NearChurchesFragment;
import com.frama.miserend.hu.database.manager.DatabaseDownloaderTask;
import com.frama.miserend.hu.database.manager.DatabaseManager;
import com.frama.miserend.hu.di.components.HomeScreenComponent;
import com.frama.miserend.hu.home.pages.map.ChurchesMapFragment;
import com.frama.miserend.hu.home.pages.masses.MassesFragment;

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
        viewModel.getDatabaseState().observe(this, databaseState -> {
            switch (databaseState) {
                case UP_TO_DATE:
                    showFragment(new NearChurchesFragment());
                    break;
                case NOT_FOUND:
                    downloadDatabase();
                    break;
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_churches:
                    showFragment(new NearChurchesFragment());
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
        });
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    private void downloadDatabase() {
        new DatabaseDownloaderTask(this, new DatabaseDownloaderTask.OnDbDownloadedListener() {
            @Override
            public void onDbDownloadStarted() {

            }

            @Override
            public void onDbDownloadFinished(boolean success) {
                showFragment(new NearChurchesFragment());
            }
        }).execute(DatabaseManager.DATABASE_URL);
    }
}
