package com.frama.miserend.hu.home.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.base.FragmentHostActivity;
import com.frama.miserend.hu.database.dialog.DatabaseDialogCallback;
import com.frama.miserend.hu.database.dialog.DatabaseMissingDialogFragment;
import com.frama.miserend.hu.database.dialog.DatabaseUpdateAvailableDialogFragment;
import com.frama.miserend.hu.database.local.LocalDatabase;
import com.frama.miserend.hu.database.miserend.manager.DatabaseState;
import com.frama.miserend.hu.home.pages.churches.view.ChurchesFragment;
import com.frama.miserend.hu.home.pages.map.view.ChurchesMapFragment;
import com.frama.miserend.hu.home.pages.masses.view.MassesFragment;
import com.frama.miserend.hu.home.viewmodel.HomeViewModel;
import com.frama.miserend.hu.location.LocationManager;
import com.frama.miserend.hu.router.Router;
import com.frama.miserend.hu.search.SearchParams;
import com.frama.miserend.hu.search.searchbar.CustomSearchBar;
import com.frama.miserend.hu.search.suggestions.Suggestion;
import com.frama.miserend.hu.search.suggestions.advanced.AdvancedSearchSuggestion;
import com.frama.miserend.hu.search.suggestions.church.ChurchSuggestion;
import com.frama.miserend.hu.search.suggestions.city.CitySuggestion;
import com.frama.miserend.hu.search.suggestions.recent.RecentSearchSuggestion;
import com.frama.miserend.hu.search.suggestions.viewmodel.SuggestionViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Balazs on 2018. 02. 10..
 */

public class HomeScreenActivity extends FragmentHostActivity implements DatabaseDialogCallback {

    @Inject
    HomeViewModel viewModel;
    @Inject
    SuggestionViewModel suggestionViewModel;
    @Inject
    Router router;
    @Inject
    LocationManager locationManager;

    @BindView(R.id.search_bar)
    CustomSearchBar searchBar;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.search_fader)
    View searchFader;

    ProgressDialog databaseDownloadingDialog;

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
                if (enabled) {
                    suggestionViewModel.updateSuggestions("");
                }
            }

            @Override
            public void onSuggestionSelected(Suggestion suggestion) {
                if (suggestion instanceof ChurchSuggestion) {
                    router.showChurchDetails(((ChurchSuggestion) suggestion).getData());
                } else if (suggestion instanceof AdvancedSearchSuggestion) {
                    router.showAdvancedSearch();
                } else if (suggestion instanceof CitySuggestion) {
                    SearchParams searchParams = new SearchParams();
                    searchParams.setCity(((CitySuggestion) suggestion).getData());
                    router.showSearchResults(searchParams);
                } else if (suggestion instanceof RecentSearchSuggestion) {
                    SearchParams searchParams = new SearchParams(((RecentSearchSuggestion) suggestion).getData());
                    router.showSearchResults(searchParams);
                }
            }

            @Override
            public void onSearchConfirmed(String searchTerm) {
                suggestionViewModel.addRecentSearch(searchTerm);
                searchBar.close();
                router.showSearchResults(new SearchParams(searchTerm));
            }
        });
    }

    private void onSuggestionsChanged(List<Suggestion> suggestions) {
        searchBar.updateLastSuggestions(suggestions);
    }

    private void onDatabaseStateChanged(DatabaseState databaseState) {
        switch (databaseState) {
            case UP_TO_DATE:
                hideDownloadingDialog();
                showFragment(new ChurchesFragment());
                break;
            case UPDATE_AVAILABLE:
                hideDownloadingDialog();
                showDatabaseUpdateDialog();
                break;
            case NOT_FOUND:
                hideDownloadingDialog();
                showDatabaseMissingDialog();
                break;
            case DOWNLOADING:
                databaseDownloadingDialog = ProgressDialog.show(this, null, getString(R.string.dialog_db_downloading));
                databaseDownloadingDialog.setCancelable(false);
                break;
        }
    }

    private void hideDownloadingDialog() {
        if (databaseDownloadingDialog != null) {
            databaseDownloadingDialog.cancel();
        }
    }

    private void showDatabaseMissingDialog() {
        DialogFragment newFragment = DatabaseMissingDialogFragment.newInstance();
        newFragment.show(getSupportFragmentManager(), "dialog");
    }

    private void showDatabaseUpdateDialog() {
        DialogFragment newFragment = DatabaseUpdateAvailableDialogFragment.newInstance();
        newFragment.show(getSupportFragmentManager(), "dialog");
    }

    public void downloadDatabase() {
        viewModel.downloadDatabase();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (LocationManager.LOCATION_SETTINGS_REQUEST_CODE == requestCode) {
            locationManager.getLastKnownLocation();
        } else if (LocationManager.LOCATION_PERMISSION_REQUEST_CODE == requestCode) {
            locationManager.getLastKnownLocation();
        }
    }

    @Override
    public void onDownloadClicked() {
        downloadDatabase();
    }

    @Override
    public void onDontDownloadClicked(boolean dbMissing) {
        if (dbMissing) {
            finish();
        } else {
            showFragment(new ChurchesFragment());
        }
    }
}