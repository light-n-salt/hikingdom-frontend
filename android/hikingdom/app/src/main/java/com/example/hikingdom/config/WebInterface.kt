package com.example.hikingdom.config

import android.content.Context
import android.content.Intent
import android.util.Log
import android.webkit.JavascriptInterface
import androidx.viewbinding.ViewBinding
import com.example.hikingdom.data.entities.User
import com.example.hikingdom.data.local.AppDatabase
import com.example.hikingdom.ui.BaseActivity
import com.example.hikingdom.ui.login.LoginActivity
import com.example.hikingdom.ui.main.MainActivity
import com.example.hikingdom.utils.deleteJWT
import com.example.hikingdom.utils.saveJWT
import com.google.gson.Gson


// 리액트 WebView로부터 호출되는 함수들을 정의한 클래스
class WebInterface(private val mContext: Context) {
    private val gson = Gson()

    // 로그인 시 메인 엑티비티로 화면 전환
    @JavascriptInterface
    fun login() {
        // 기존 액티비티 종료 후, 메인 액티비티로 전환
        val intent = Intent(mContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        mContext.startActivity(intent)
    }

    // 로그아웃 시 메인 엑티비티로 화면 전환
    @JavascriptInterface
    fun unlogin() {
        // 기존 액티비티 종료 후, 로그인 액티비티로 전환
        val intent = Intent(mContext, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        mContext.startActivity(intent)
    }

    // JWT 토큰 저장 함수
    @JavascriptInterface
    fun saveToken(accessToken: String, refreshToken: String) {
        saveJWT(accessToken, refreshToken)
    }

    // JWT 토큰 제거 함수
    @JavascriptInterface
    fun removeToken() {
        deleteJWT()
    }

    // 사용자 정보 저장 함수
    @JavascriptInterface
    fun saveUserInfo(jsonUserInfo: String) {
        // change the json data to Java object
        val userInfo = gson.fromJson(jsonUserInfo, User::class.java)
        val db = AppDatabase.getInstance(mContext)

        db?.userDao()?.deleteAll() // 기존 정보 삭제
        db?.userDao()?.insert(userInfo) // 새 정보 입력
    }

}