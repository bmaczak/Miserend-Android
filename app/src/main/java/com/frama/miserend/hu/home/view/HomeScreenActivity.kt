package com.frama.miserend.hu.home.view

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.MenuItem
import android.view.View

import com.frama.miserend.hu.R
import com.frama.miserend.hu.base.FragmentHostActivity
import com.frama.miserend.hu.database.dialog.DatabaseDialogCallback
import com.frama.miserend.hu.database.dialog.DatabaseMissingDialogFragment
import com.frama.miserend.hu.database.dialog.DatabaseUpdateAvailableDialogFragment
import com.frama.miserend.hu.database.miserend.manager.DatabaseState
import com.frama.miserend.hu.home.pages.churches.view.ChurchesFragment
import com.frama.miserend.hu.home.pages.map.view.ChurchesMapFragment
import com.frama.miserend.hu.home.pages.masses.view.MassesFragment
import com.frama.miserend.hu.home.viewmodel.HomeViewModel
import com.frama.miserend.hu.location.LocationPermissionHelper
import com.frama.miserend.hu.router.Router
import com.frama.miserend.hu.search.SearchParams
import com.frama.miserend.hu.search.searchbar.CustomSearchBar
import com.frama.miserend.hu.search.suggestions.Suggestion
import com.frama.miserend.hu.search.suggestions.advanced.AdvancedSearchSuggestion
import com.frama.miserend.hu.search.suggestions.church.ChurchSuggestion
import com.frama.miserend.hu.search.suggestions.city.CitySuggestion
import com.frama.miserend.hu.search.suggestions.recent.RecentSearchSuggestion
import com.frama.miserend.hu.search.suggestions.viewmodel.SuggestionViewModel

import javax.inject.Inject

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by Balazs on 2018. 02. 10..
 */

class HomeScreenActivity : FragmentHostActivity(), DatabaseDialogCallback {

    @Inject
    internal var viewModel: HomeViewModel? = null
    @Inject
    internal var suggestionViewModel: SuggestionViewModel? = null
    @Inject
    internal var router: Router? = null
    @Inject
    internal var locationPermissionHelper: LocationPermissionHelper? = null

    @BindView(R.id.search_bar)
    internal var searchBar: CustomSearchBar? = null
    @BindView(R.id.bottom_navigation)
    internal var bottomNavigationView: BottomNavigationView? = null
    @BindView(R.id.search_fader)
    internal var searchFader: View? = null

    internal var databaseDownloadingDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        ButterKnife.bind(this)
        viewModel!!.databaseState.observe(this, Observer<DatabaseState> { this.onDatabaseStateChanged(it) })
        bottomNavigationView!!.setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener { this.onNavigationItemSelected(it) })
        suggestionViewModel!!.suggestions.observe(this, Observer<List<Suggestion>> { this.onSuggestionsChanged(it) })
        searchFader!!.setOnClickListener { view -> searchBar!!.close() }
        searchBar!!.setSearchBarCallback(object : CustomSearchBar.SearchBarCallback {
            override fun onSearchTermChanged(searchTerm: String) {
                suggestionViewModel!!.updateSuggestions(searchTerm)
            }

            override fun onSearchStateChanged(enabled: Boolean) {
                searchFader!!.visibility = if (enabled) View.VISIBLE else View.GONE
                if (enabled) {
                    suggestionViewModel!!.updateSuggestions("")
                }
            }

            override fun onSuggestionSelected(suggestion: Suggestion<*>) {
                if (suggestion is ChurchSuggestion) {
                    router!!.showChurchDetails(suggestion.data)
                } else if (suggestion is AdvancedSearchSuggestion) {
                    router!!.showAdvancedSearch()
                } else if (suggestion is CitySuggestion) {
                    val searchParams = SearchParams()
                    searchParams.city = suggestion.data
                    router!!.showSearchResults(searchParams)
                } else if (suggestion is RecentSearchSuggestion) {
                    val searchParams = SearchParams(suggestion.data)
                    router!!.showSearchResults(searchParams)
                }
            }

            override fun onSearchConfirmed(searchTerm: String) {
                suggestionViewModel!!.addRecentSearch(searchTerm)
                searchBar!!.close()
                router!!.showSearchResults(SearchParams(searchTerm))
            }
        })
        if (!locationPermissionHelper!!.hasLocationPermission()) {
            locationPermissionHelper!!.showPermissionRequestPopup()
        }
    }

    private fun onSuggestionsChanged(suggestions: List<Suggestion<*>>) {
        searchBar!!.updateLastSuggestions(suggestions)
    }

    private fun onDatabaseStateChanged(databaseState: DatabaseState) {
        when (databaseState) {
            DatabaseState.UP_TO_DATE -> {
                hideDownloadingDialog()
                showFragment(ChurchesFragment())
            }
            DatabaseState.UPDATE_AVAILABLE -> {
                hideDownloadingDialog()
                showDatabaseUpdateDialog()
            }
            DatabaseState.DATABASE_CORRUPT -> {
                hideDownloadingDialog()
                showDatabaseCorruptDialog()
            }
            DatabaseState.NOT_FOUND -> {
                hideDownloadingDialog()
                showDatabaseMissingDialog()
            }
            DatabaseState.DOWNLOADING -> {
                databaseDownloadingDialog = ProgressDialog.show(this, null, getString(R.string.dialog_db_downloading))
                databaseDownloadingDialog!!.setCancelable(false)
            }
        }
    }

    private fun hideDownloadingDialog() {
        if (databaseDownloadingDialog != null) {
            databaseDownloadingDialog!!.cancel()
        }
    }

    private fun showDatabaseMissingDialog() {
        val newFragment = DatabaseMissingDialogFragment.newInstance(R.string.dialog_db_missing_title, R.string.dialog_db_missing_message)
        newFragment.show(supportFragmentManager, "dialog")
    }

    private fun showDatabaseCorruptDialog() {
        val newFragment = DatabaseMissingDialogFragment.newInstance(R.string.dialog_db_corrupt_title, R.string.dialog_db_corrupt_message)
        newFragment.show(supportFragmentManager, "dialog")
    }

    private fun showDatabaseUpdateDialog() {
        val newFragment = DatabaseUpdateAvailableDialogFragment.newInstance()
        newFragment.show(supportFragmentManager, "dialog")
    }

    fun downloadDatabase() {
        viewModel!!.downloadDatabase()
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }

    fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_churches -> {
                showFragment(ChurchesFragment())
                return true
            }
            R.id.action_masses -> {
                showFragment(MassesFragment())
                return true
            }
            R.id.action_map -> {
                showFragment(ChurchesMapFragment())
                return true
            }
            else -> return false
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        viewModel!!.retryLocation()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (LocationPermissionHelper.LOCATION_SETTINGS_REQUEST_CODE == requestCode) {
            viewModel!!.retryLocation()
        } else if (LocationPermissionHelper.LOCATION_PERMISSION_REQUEST_CODE == requestCode) {
            viewModel!!.retryLocation()
        }
    }

    override fun onDownloadClicked() {
        downloadDatabase()
    }

    override fun onDontDownloadClicked(dbMissing: Boolean) {
        if (dbMissing) {
            finish()
        } else {
            showFragment(ChurchesFragment())
        }
    }
}