package com.frama.miserend.hu.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.churchlist.NearChurchesFragment;
import com.frama.miserend.hu.database.manager.DatabaseDownloaderTask;
import com.frama.miserend.hu.database.manager.DatabaseManager;
import com.frama.miserend.hu.di.components.HomeScreenComponent;

import javax.inject.Inject;

/**
 * Created by Balazs on 2018. 02. 10..
 */

public class HomeScreenActivity extends AppCompatActivity {

    @Inject
    HomeViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeScreenComponent.Injector.inject(this);
        setContentView(R.layout.activity_home);
        viewModel.getDatabaseState().observe(this, databaseState -> {
            switch (databaseState) {
                case UP_TO_DATE:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NearChurchesFragment()).commit();
                    break;
                case NOT_FOUND:
                    downloadDatabase();
                    break;
            }
        });
    }

    private void downloadDatabase() {
        new DatabaseDownloaderTask(this, new DatabaseDownloaderTask.OnDbDownloadedListener() {
            @Override
            public void onDbDownloadStarted() {

            }

            @Override
            public void onDbDownloadFinished(boolean success) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NearChurchesFragment()).commit();
            }
        }).execute(DatabaseManager.DATABASE_URL);
    }
}
