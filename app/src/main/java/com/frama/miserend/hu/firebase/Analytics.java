package com.frama.miserend.hu.firebase;

import android.app.Activity;

import com.google.firebase.analytics.FirebaseAnalytics;

public class Analytics {


    private FirebaseAnalytics firebaseAnalytics;

    public Analytics(FirebaseAnalytics firebaseAnalytics) {
        this.firebaseAnalytics = firebaseAnalytics;
    }

    public void setCurrentScreen(Activity activity, String screenName) {
        firebaseAnalytics.setCurrentScreen(activity, screenName, null);
    }

    public static class ScreenNames {
        public static final String NEAR_CHURCHES = "Near churches";
        public static final String FAVORITE_CHURCHES = "Favorite churches";
        public static final String MASSES = "Masses";
        public static final String MAP = "Map";
        public static final String CHURCH_DETAILS = "Church details";
        public static final String ADVANCED_SEARCH = "Advanced search";
        public static final String SEARCH_RESULTS = "Search results";
    }
}
