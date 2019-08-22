package ru.aholmanov.search_artist_app.repository

import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceRepository @Inject constructor(private val preference: SharedPreferences) {

    fun isViewedIntro(): Boolean {
        return preference.getBoolean(ARG_FIRST_LAUNCH, false)
    }

    fun setIntroIsViewed() {
        preference.edit().putBoolean(ARG_FIRST_LAUNCH, true).apply()
    }

    private val ARG_FIRST_LAUNCH = "first launch"

}