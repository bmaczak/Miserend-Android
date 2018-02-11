package com.frama.miserend.hu.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.churchlist.NearChurchesFragment;
import com.frama.miserend.hu.database.DatabaseDownloaderTask;
import com.frama.miserend.hu.database.DatabaseManager;

/**
 * Created by Balazs on 2018. 02. 10..
 */

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (DatabaseManager.isDbExist(this)) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NearChurchesFragment()).commit();
        } else {
            downloadDatabase();
        }
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
