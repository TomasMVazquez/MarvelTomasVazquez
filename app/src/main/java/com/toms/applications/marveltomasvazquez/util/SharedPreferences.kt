package com.toms.applications.marveltomasvazquez.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * To start or not the onBoarding when initializing the app
 */
fun getSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
}

object SharedPreferencesKeys {
    const val ON_BOARDING = "onBoarding"
}

fun onFinishOnBoarding(context: Context) {
    getSharedPreferences(context).edit { putBoolean(SharedPreferencesKeys.ON_BOARDING, true) }
}

fun onBoardingFinished(context: Context): Boolean {
    return getSharedPreferences(context).getBoolean(SharedPreferencesKeys.ON_BOARDING, false)
}