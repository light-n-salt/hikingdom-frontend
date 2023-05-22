package com.example.hikingdom.ui.main.home

import android.os.Build
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import com.example.hikingdom.ApplicationClass.Companion.HOME_WEB_URL
import com.example.hikingdom.config.WebInterface
import com.example.hikingdom.databinding.FragmentHomeBinding
import com.example.hikingdom.ui.BaseFragment
import com.example.hikingdom.utils.getRefreshToken

class HomeFragment(): BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun initAfterBinding() {
        webViewSetting(binding.homeWebview, HOME_WEB_URL)
        swipeReloadWebview(binding.swipeRefreshLayout, binding.homeWebview)
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}