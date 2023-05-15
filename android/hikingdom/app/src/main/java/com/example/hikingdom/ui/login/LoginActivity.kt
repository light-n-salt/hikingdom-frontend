package com.example.hikingdom.ui.login

import android.util.Log
import android.view.View
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity: BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), LoginView {

    override fun initAfterBinding() {
        webViewSetting()

// 예시
//        val api = RetrofitTokenInstance.getInstance().create(AuthRetrofitInterface::class.java)
//        binding.button3.setOnClickListener{
//            api.getUserInfo().enqueue(object: Callback<UserResponse> {
//                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
//                    Log.d("user!", "${response.body()}")
//                }
//                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
//                    Log.d("user!", "fail")
//                }
//            })
//        }
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
        webView.loadUrl(ApplicationClass.INDEX_WEB_URL)
    }
}