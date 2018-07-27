package com.frama.miserend.hu.router;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.churchdetails.view.ChurchDetailsActivity;
import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.search.SearchParams;
import com.frama.miserend.hu.search.advanced.AdvancedSearchActivity;
import com.frama.miserend.hu.search.result.view.SearchResultActivity;

import java.util.Locale;

/**
 * Created by Balazs on 2018. 02. 18..
 */

public class Router {

    public static class IntentExtra {
        public static String CHURCH_ID = "church_id";
        public static String SEARCH_PARAMS = "search_params";
    }

    private Activity activity;

    public Router(Activity activity) {
        this.activity = activity;
    }

    public void showChurchDetails(Church church) {
        Intent churchDetailsIntent = new Intent(activity, ChurchDetailsActivity.class);
        churchDetailsIntent.putExtra(IntentExtra.CHURCH_ID, church.getId());
        activity.startActivity(churchDetailsIntent);
    }

    public void showAdvancedSearch() {
        activity.startActivity(new Intent(activity, AdvancedSearchActivity.class));
    }

    public void showSearchResults(SearchParams searchParams) {
        Intent searchResultIntent = new Intent(activity, SearchResultActivity.class);
        searchResultIntent.putExtra(IntentExtra.SEARCH_PARAMS, searchParams);
        activity.startActivity(searchResultIntent);
    }

    public void startGoogleNavigation(Church church) {
        String navigationUrl = String.format(Locale.ENGLISH,
                activity.getResources().getString(R.string.navigation_url),
                church.getLat(), church.getLon());
        Intent navIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(navigationUrl));
        activity.startActivity(navIntent);
    }

    public void showOnMap(Church church) {
        String uri = String.format(Locale.ENGLISH, "geo:0,0?q=") + android.net.Uri.encode(String.format(Locale.ENGLISH, "%s@%f,%f", church.getName(), church.getLat(), church.getLon()), "UTF-8");
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        activity.startActivity(intent);
    }
}
