package com.frama.miserend.hu.gallery;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;

import com.frama.miserend.hu.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryActivity extends AppCompatActivity {

    public final static String EXTRA_IMAGE_URLS = "images";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.gallery_view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        setupActionBar();
        List<String> imageUrls = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);
        viewPager.setAdapter(new GalleryAdapter(imageUrls));
    }

    private void setupActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
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
}
