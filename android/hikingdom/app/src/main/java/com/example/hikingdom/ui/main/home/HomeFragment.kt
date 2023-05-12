package com.example.hikingdom.ui.main.home

import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import com.example.hikingdom.ApplicationClass.Companion.HOME_WEB_URL
import com.example.hikingdom.config.WebInterface
import com.example.hikingdom.databinding.FragmentHomeBinding
import com.example.hikingdom.ui.BaseFragment

class HomeFragment(): BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun initAfterBinding() {
        webViewSetting(activityContext, binding.homeWebview, HOME_WEB_URL)
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}