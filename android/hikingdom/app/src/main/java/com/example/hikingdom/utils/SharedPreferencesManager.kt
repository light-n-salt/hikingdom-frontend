package com.example.hikingdom.utils

import com.example.hikingdom.ApplicationClass.Companion.ACCESS_TOKEN_KEY
import com.example.hikingdom.ApplicationClass.Companion.REFRESH_TOKEN_KEY
import com.example.hikingdom.ApplicationClass.Companion.mSharedPreferences

// ApplicatonClass에서 선언한 sharedPreference에 대한 동작을 정의한 함수
fun saveJWT(accessToken: String, refreshToken: String) {
    val editor = mSharedPreferences.edit()
    editor.putString(ACCESS_TOKEN_KEY, accessToken)
    editor.putString(REFRESH_TOKEN_KEY, refreshToken)
    editor.apply()
}

fun deleteJWT() {
    val editor = mSharedPreferences.edit()
    editor.remove(ACCESS_TOKEN_KEY)
    editor.remove(REFRESH_TOKEN_KEY)
    editor.apply()
}
fun getAccessToken(): String? = mSharedPreferences.getString(ACCESS_TOKEN_KEY, null)
fun getRefreshToken(): String? = mSharedPreferences.getString(REFRESH_TOKEN_KEY, null)
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
