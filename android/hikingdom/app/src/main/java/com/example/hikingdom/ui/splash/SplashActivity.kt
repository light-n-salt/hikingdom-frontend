package com.example.hikingdom.ui.splash

import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.hikingdom.R
import com.example.hikingdom.databinding.ActivitySplashBinding
import com.example.hikingdom.ui.BaseActivity
import com.example.hikingdom.ui.login.LoginActivity
import com.example.hikingdom.ui.main.MainActivity

class SplashActivity: BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate), SplashView {

    override fun initAfterBinding() {

        val logo: ImageView = binding.logo
        val catchphrase: TextView = binding.catchphrase
        val slideFromLeftAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_from_left)
        val slideFromRightAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_from_right)
        logo.startAnimation(slideFromLeftAnimation)
        catchphrase.startAnimation(slideFromRightAnimation)

        Handler(Looper.getMainLooper()).postDelayed({
//            autoLogin()
            startActivityWithClear(LoginActivity::class.java)
        }, 2000)
    }

    private fun autoLogin() {
        // 자동 로그인 (JWT 토큰 여부 확인 -> api 요청으로 유효성 확인)
//        AuthService.autoLogin(this)
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