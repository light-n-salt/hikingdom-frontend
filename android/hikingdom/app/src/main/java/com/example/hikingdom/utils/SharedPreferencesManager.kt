package com.example.hikingdom.utils

import com.example.hikingdom.ApplicationClass.Companion.ACCESS_TOKEN_KEY
import com.example.hikingdom.ApplicationClass.Companion.REFRESH_TOKEN_KEY
import com.example.hikingdom.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.example.hikingdom.ApplicationClass.Companion.mSharedPreferences

// ApplicatonClass에서 선언한 sharedPreference에 대한 접근 함수를 정의

// JWT 토큰 저장
fun saveJWT(accessToken: String, refreshToken: String) {
    val editor = mSharedPreferences.edit()
    editor.putString(ACCESS_TOKEN_KEY, accessToken)
    editor.putString(REFRESH_TOKEN_KEY, refreshToken)
    editor.apply()
}

// JWT 토큰 삭제
fun deleteJWT() {
    val editor = mSharedPreferences.edit()
    editor.remove(ACCESS_TOKEN_KEY)
    editor.remove(REFRESH_TOKEN_KEY)
    editor.apply()
}

// Access JWt Token 읽기
fun getAccessToken(): String? = mSharedPreferences.getString(ACCESS_TOKEN_KEY, null)

// Refresh JWt Token 읽기
fun getRefreshToken(): String? = mSharedPreferences.getString(REFRESH_TOKEN_KEY, null)

fun getJwt(): String? = mSharedPreferences.getString(X_ACCESS_TOKEN, null)

fun saveIsLocationServiceRunning(boolean: Boolean){ //
    val editor = mSharedPreferences.edit()
    editor.putBoolean("isLocationServiceRunning", boolean)
    editor.apply()
}

fun getIsLocationServiceRunning(): Boolean =
    mSharedPreferences.getBoolean("isLocationServiceRunning", false)

fun saveIsMeetup(boolean: Boolean){ //
    val editor = mSharedPreferences.edit()
    editor.putBoolean("isMeetup", boolean)
    editor.apply()
}

fun getIsMeetup(): Boolean =
    mSharedPreferences.getBoolean("isMeetup", false)

fun saveIsSummit(boolean: Boolean){ //
    val editor = mSharedPreferences.edit()
    editor.putBoolean("isSummit", boolean)
    editor.apply()
}

fun getIsSummit(): Boolean =
    mSharedPreferences.getBoolean("isSummit", false)
