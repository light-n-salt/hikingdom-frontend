package com.example.hikingdom.ui.main

import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hikingdom.R
import com.example.hikingdom.databinding.ActivityMainBinding
import com.example.hikingdom.ui.BaseActivity
import java.security.MessageDigest


class MainActivity: BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private lateinit var navHostFragment: NavHostFragment

    override fun initAfterBinding() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController: NavController = navHostFragment.findNavController()

        binding.mainBottomNavigation.setupWithNavController(navController)
        binding.mainBottomNavigation.itemIconTintList = null

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}