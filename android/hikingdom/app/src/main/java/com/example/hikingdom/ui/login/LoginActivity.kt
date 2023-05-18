package com.example.hikingdom.ui.login

import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import com.example.hikingdom.ApplicationClass
import com.example.hikingdom.config.WebInterface
import com.example.hikingdom.data.remote.api.RetrofitTokenInstance
import com.example.hikingdom.data.remote.auth.AuthRetrofitInterface
import com.example.hikingdom.data.remote.auth.UserResponse
import com.example.hikingdom.databinding.ActivityLoginBinding

import com.example.hikingdom.ui.BaseActivity
import com.example.hikingdom.ui.main.MainActivity
import com.example.hikingdom.utils.getRefreshToken
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        val webView = binding.loginWebview
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.addJavascriptInterface(WebInterface(this), "Kotlin")
        webView.loadUrl(ApplicationClass.INDEX_WEB_URL)

        // 페이지가 로드 되었을 때 fcm 토큰을 전달해줌
        webView.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(ApplicationClass.TAG, "Fetching FCM registration token failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new FCM registration token
                    val fcmToken = task.result
                    webView.evaluateJavascript("saveFcmToken('$fcmToken')", null)
                })
            }
        })
    }

    fun getFcmToken(): String {
        var token = ""

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(ApplicationClass.TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            token = task.result
            Log.d("toekn", "token: $token")
        })

        return token
    }
}