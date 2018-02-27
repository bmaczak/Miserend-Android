package com.frama.miserend.hu.router;

import android.app.Activity;
import android.content.Intent;

import com.frama.miserend.hu.churchdetails.ChurchDetailsActivity;
import com.frama.miserend.hu.database.miserend.entities.Church;

/**
 * Created by Balazs on 2018. 02. 18..
 */

public class Router {

    public static class IntentExtra {
        public static String CHURCH_ID = "church_id";
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
}
