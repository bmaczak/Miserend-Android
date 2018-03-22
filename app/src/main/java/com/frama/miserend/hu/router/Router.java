package com.frama.miserend.hu.router;

import android.app.Activity;
import android.content.Intent;

import com.frama.miserend.hu.churchdetails.ChurchDetailsActivity;
import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.search.SearchParams;
import com.frama.miserend.hu.search.advanced.AdvancedSearchActivity;
import com.frama.miserend.hu.search.result.SearchResultActivity;

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
}
