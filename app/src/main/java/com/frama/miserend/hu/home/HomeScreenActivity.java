package com.frama.miserend.hu.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.database.manager.DatabaseState;
import com.frama.miserend.hu.di.components.HomeScreenComponent;
import com.frama.miserend.hu.home.pages.churches.ChurchesFragment;
import com.frama.miserend.hu.home.pages.map.ChurchesMapFragment;
import com.frama.miserend.hu.home.pages.masses.MassesFragment;
import com.frama.miserend.hu.search.suggestions.CitySuggestion;
import com.frama.miserend.hu.search.suggestions.CustomSuggestionAdapter;
import com.frama.miserend.hu.search.suggestions.Suggestion;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Balazs on 2018. 02. 10..
 */

public class HomeScreenActivity extends AppCompatActivity {

    @Inject
    HomeViewModel viewModel;

    @BindView(R.id.search_bar)
    MaterialSearchBar materialSearchBar;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.search_fader)
    View searchFader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeScreenComponent.Injector.inject(this);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        viewModel.getDatabaseState().observe(this, this::onDatabaseStateChanged);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        getSuggestions();
        searchFader.setOnClickListener(view -> {
            materialSearchBar.clearFocus();
            materialSearchBar.hideSuggestionsList();
            materialSearchBar.disableSearch();
        });
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

    private void getSuggestions() {
        List<Suggestion> suggestions = new ArrayList<>();
        suggestions.add(new CitySuggestion("Budapest"));
        suggestions.add(new CitySuggestion("Esztergom"));
        suggestions.add(new CitySuggestion("Székesfehérvár"));

        CustomSuggestionAdapter suggestionAdapter = new CustomSuggestionAdapter(getLayoutInflater(), suggestions);
        materialSearchBar.setCustomSuggestionAdapter(suggestionAdapter);

        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                searchFader.setVisibility(enabled ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
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