package com.example.hikingdom.ui.login

import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import com.example.hikingdom.ApplicationClass
import com.example.hikingdom.config.WebInterface
import com.example.hikingdom.data.remote.auth.Auth
import com.example.hikingdom.databinding.ActivityLoginBinding

import com.example.hikingdom.ui.BaseActivity
import com.example.hikingdom.ui.main.MainActivity
import com.example.hikingdom.utils.getRefreshToken

class LoginActivity: BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), LoginView {

    override fun initAfterBinding() {
        webViewSetting()
    }

    // 로그인 성공 시, 동작시킬 함수
    override fun onLoginSuccess() {
        startActivityWithClear(MainActivity::class.java)
    }

    // WebView 셋팅
    fun webViewSetting(){
        var refreshToken = getRefreshToken()
        Log.d("refresh", "$refreshToken")

        val webView = binding.loginWebview
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.addJavascriptInterface(WebInterface(this), "Kotlin")
//        webView.evaluateJavascript("sendRefreshToken('$refreshToken')", null)
        webView.loadUrl(ApplicationClass.INDEX_WEB_URL)
    }
}