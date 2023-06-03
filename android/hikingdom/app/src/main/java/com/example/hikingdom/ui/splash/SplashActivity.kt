package com.example.hikingdom.ui.splash

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.hikingdom.R
import com.example.hikingdom.data.remote.api.RetrofitInstance
import com.example.hikingdom.data.remote.auth.AuthRetrofitInterface
import com.example.hikingdom.data.remote.auth.TokenRequest
import com.example.hikingdom.data.remote.auth.TokenResponse
import com.example.hikingdom.databinding.ActivitySplashBinding
import com.example.hikingdom.ui.BaseActivity
import com.example.hikingdom.ui.login.LoginActivity
import com.example.hikingdom.ui.main.MainActivity
import com.example.hikingdom.utils.deleteJWT
import com.example.hikingdom.utils.getRefreshToken
import com.example.hikingdom.utils.saveJWT
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("CustomSplashScreen")
class SplashActivity: BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate), SplashView {

    override fun initAfterBinding() {
        Log.d("autoLogin1", "autoLogin")
        autoLogin() // 자동 로그인 로직 실행

        val image: ImageView = binding.image
        val logo: ImageView = binding.logo
        val slideFromDownAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_from_down)
        val slideFromUpAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_from_up)
        logo.startAnimation(slideFromDownAnimation)
        image.startAnimation(slideFromUpAnimation)
    }

    private fun autoLogin() {
        Log.d("autoLogin2", "autoLogin")
        val refreshToken = getRefreshToken() // refreshToken 유뮤 확인
        val api = RetrofitInstance.getInstance().create(AuthRetrofitInterface::class.java)  // Retrofit 객체 생성(인터셉터 없는)

        // 저장된 refresh 토큰이 없을 경우 Login Activity로 이동
        if (refreshToken == null) {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivityWithClear(LoginActivity::class.java)
            }, 3000)
        }
        
        // 저장된 refresh 토큰이 있을 경우, access토큰 재발급 로직을 통해 refresh 토큰 유효성 검사
        else {
            var tokenRequest = TokenRequest(refreshToken)
            api.refreshTokens(tokenRequest).enqueue(object: Callback<TokenResponse> {
                override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                    var responseBody = response.body()
                    val newAccessToken = responseBody?.result?.accessToken  // 새로 발급받은 access 토큰
                    val newRefreshToken = responseBody?.result?.refreshToken // 새로 발급 받은 refresh 토큰

                    if (newAccessToken != null && newRefreshToken != null) {    // 둘 다 제대로 발급 되었을 경우, 메인 액티비티로 이동
                        saveJWT(newAccessToken, newRefreshToken)
                        Handler(Looper.getMainLooper()).postDelayed({
                            startActivityWithClear(MainActivity::class.java)
                        }, 3000)
                    } else {                                                    // 아닐 경우, 로그인 액티비티로 이동
                        deleteJWT()
                        Handler(Looper.getMainLooper()).postDelayed({
                            startActivityWithClear(LoginActivity::class.java)
                        }, 3000)
                    }
                }
                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {   // 발급에 실패한 경우도, 로그인 액티비티로 이동
                    deleteJWT()
                    Handler(Looper.getMainLooper()).postDelayed({
                        startActivityWithClear(LoginActivity::class.java)
                    }, 3000)
                }
            })
        }
    }

    override fun onAutoLoginLoading() {

    }

    override fun onAutoLoginSuccess() {
        startActivityWithClear(MainActivity::class.java)
    }

    override fun onAutoLoginFailure(code: Int, message: String) {
        startActivityWithClear(LoginActivity::class.java)
    }
}