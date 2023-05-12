package com.example.hikingdom.utils

import com.example.hikingdom.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.example.hikingdom.ApplicationClass.Companion.mSharedPreferences

fun saveJwt(jwtToken: String) {
    val editor = mSharedPreferences.edit()
    editor.putString(X_ACCESS_TOKEN, jwtToken)

    editor.apply()
}

fun getJwt(): String? = mSharedPreferences.getString(X_ACCESS_TOKEN, null)

fun saveIsLocationServiceRunning(boolean: Boolean){ //
    val editor = mSharedPreferences.edit()
    editor.putBoolean("isLocationServiceRunning", boolean)
    editor.apply()
}

fun getIsLocationServiceRunning(): Boolean =
    mSharedPreferences.getBoolean("isLocationServiceRunning", false)

fun saveIsSummit(boolean: Boolean){ //
    val editor = mSharedPreferences.edit()
    editor.putBoolean("isSummit", boolean)
    editor.apply()
}

fun getIsSummit(): Boolean =
    mSharedPreferences.getBoolean("isSummit", false)