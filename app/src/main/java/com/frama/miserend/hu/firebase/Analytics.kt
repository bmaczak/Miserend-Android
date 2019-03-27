package com.frama.miserend.hu.firebase

import android.app.Activity

import com.google.firebase.analytics.FirebaseAnalytics

class Analytics(private val firebaseAnalytics: FirebaseAnalytics) {

    fun setCurrentScreen(activity: Activity, screenName: String) = firebaseAnalytics.setCurrentScreen(activity, screenName, null)

    object ScreenNames {
        const val NEAR_CHURCHES = "Near churches"
        const val FAVORITE_CHURCHES = "Favorite churches"
        const val MASSES = "Masses"
        const val MAP = "Map"
        const val CHURCH_DETAILS = "Church details"
        const val ADVANCED_SEARCH = "Advanced search"
        const val SEARCH_RESULTS = "Search results"
    }
}
