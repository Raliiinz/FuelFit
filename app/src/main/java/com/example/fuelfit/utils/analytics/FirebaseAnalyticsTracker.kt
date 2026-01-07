package com.example.fuelfit.utils.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent

class FirebaseAnalyticsTracker(
    private val analytics: FirebaseAnalytics
) : AnalyticsTracker {

    override fun screenOpened(screen: Screen) {
        analytics.logEvent(EVENT_SCREEN_OPENED) {
            param(PARAM_SCREEN_NAME, screen.screenName)
        }
    }

    companion object {
        private const val EVENT_SCREEN_OPENED = "screen_opened"
        private const val PARAM_SCREEN_NAME = "screen_name"
    }
}
