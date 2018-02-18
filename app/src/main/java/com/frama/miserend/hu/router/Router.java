package com.frama.miserend.hu.router;

import android.app.Activity;
import android.content.Intent;

import com.frama.miserend.hu.churchdetails.ChurchDetailsActivity;
import com.frama.miserend.hu.database.miserend.entities.Church;

/**
 * Created by Balazs on 2018. 02. 18..
 */

public class Router {

    private Activity activity;

    public Router(Activity activity) {
        this.activity = activity;
    }

    public void showChurcDetails(Church church) {
        Intent churchDetailsIntent = new Intent(activity, ChurchDetailsActivity.class);
        activity.startActivity(churchDetailsIntent);
    }
}
