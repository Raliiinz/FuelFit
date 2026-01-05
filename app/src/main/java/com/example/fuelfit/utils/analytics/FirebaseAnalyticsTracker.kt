package com.example.fuelfit.utils.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent

class FirebaseAnalyticsTracker(
    private val analytics: FirebaseAnalytics
) : AnalyticsTracker {

    override fun screenOpened(screen: Screen) {
        analytics.logEvent("screen_opened") {
            param("screen_name", screen.screenName)
        }
    }
}
