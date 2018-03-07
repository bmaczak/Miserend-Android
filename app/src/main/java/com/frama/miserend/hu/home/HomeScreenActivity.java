package com.frama.miserend.hu.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.base.BaseActivity;
import com.frama.miserend.hu.database.miserend.manager.DatabaseState;
import com.frama.miserend.hu.home.pages.churches.ChurchesFragment;
import com.frama.miserend.hu.home.pages.map.ChurchesMapFragment;
import com.frama.miserend.hu.home.pages.masses.MassesFragment;
import com.frama.miserend.hu.router.Router;
import com.frama.miserend.hu.search.searchbar.CustomSearchBar;
import com.frama.miserend.hu.search.suggestions.Suggestion;
import com.frama.miserend.hu.search.suggestions.SuggestionViewModel;
import com.frama.miserend.hu.search.suggestions.church.ChurchSuggestion;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by Balazs on 2018. 02. 10..
 */

public class HomeScreenActivity extends BaseActivity {

    @Inject
    HomeViewModel viewModel;
    @Inject
    SuggestionViewModel suggestionViewModel;
    @Inject
    Router router;

    @BindView(R.id.search_bar)
    CustomSearchBar searchBar;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.search_fader)
    View searchFader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        viewModel.getDatabaseState().observe(this, this::onDatabaseStateChanged);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        suggestionViewModel.getSuggestions().observe(this, this::onSuggestionsChanged);
        searchFader.setOnClickListener(view -> searchBar.close());
        searchBar.setSearchBarCallback(new CustomSearchBar.SearchBarCallback() {
            @Override
            public void onSearchTermChanged(String searchTerm) {
                suggestionViewModel.updateSuggestions(searchTerm);
            }

            @Override
            public void onSearchStateChanged(boolean enabled) {
                searchFader.setVisibility(enabled ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onSuggestionSelected(Suggestion suggestion) {
                if (suggestion instanceof ChurchSuggestion) {
                    router.showChurchDetails(((ChurchSuggestion) suggestion).getData());
                }
            }
        });
    }

    private void onSuggestionsChanged(List<Suggestion> suggestions) {
        searchBar.updateLastSuggestions(suggestions);
    }

    private void onDatabaseStateChanged(DatabaseState databaseState) {
        switch (databaseState) {
            case UPDATE_AVAILABLE: //TODO Update available dialog
            case UP_TO_DATE:
                showFragment(new ChurchesFragment());
                break;
            case NOT_FOUND: //TODO Ask user before download
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