package com.example.hikingdom.ui.main

import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import android.webkit.WebView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hikingdom.R
import com.example.hikingdom.databinding.ActivityMainBinding
import com.example.hikingdom.ui.BaseActivity
import java.security.MessageDigest


class MainActivity: BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    
    private lateinit var navHostFragment: NavHostFragment // 네비게이션으로 웹뷰를 호스팅할 Fragment 영역

    override fun initAfterBinding() {
        
        // Fragment 영역에 대한 NavController 탐색
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController: NavController = navHostFragment.findNavController()

        // Bottom 네비게이션에 Fragment영역에 대한 NavController 할당
        binding.mainBottomNavigation.setupWithNavController(navController)
        binding.mainBottomNavigation.itemIconTintList = null

        // 리액트 웹뷰 디버깅
        WebView.setWebContentsDebuggingEnabled(true)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}