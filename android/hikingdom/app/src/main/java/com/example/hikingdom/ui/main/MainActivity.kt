package com.example.hikingdom.ui.main

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.hikingdom.R
import com.example.hikingdom.data.remote.api.RetrofitTokenInstance
import com.example.hikingdom.data.remote.auth.AuthRetrofitInterface
import com.example.hikingdom.data.remote.auth.UserResponse
import com.example.hikingdom.databinding.ActivityMainBinding
import com.example.hikingdom.ui.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity: BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    
    private lateinit var navHostFragment: NavHostFragment // 네비게이션으로 웹뷰를 호스팅할 Fragment 영역

    override fun initAfterBinding() {
        
        // Fragment 영역에 대한 NavController 탐색
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController: NavController = navHostFragment.findNavController()

        // Bottom 네비게이션에 Fragment영역에 대한 NavController 할당
        binding.mainBottomNavigation.setupWithNavController(navController)
        binding.mainBottomNavigation.itemIconTintList = null

        val myPageMenuItem = binding.mainBottomNavigation.menu.findItem(R.id.menu_myPageFragment)

        var profileImgUrl = db?.userDao()?.getUser()?.profileUrl
        if (profileImgUrl != null){
            Glide.with(this).load(profileImgUrl).circleCrop().into(object: CustomTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable, transition: com.bumptech.glide.request.transition.Transition<in Drawable>?) {
                    // 리소스가 준비되면 아이콘을 설정합니다.
                    myPageMenuItem.icon = resource
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                    // 아이콘 로드가 취소될 때의 동작을 정의합니다.
                    // 이 경우에는 기본 아이콘을 설정합니다.
                    myPageMenuItem.icon = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_mypage)
                }
            })
        }

        // 키보드 상태 변경 감지를 위한 ViewTreeObserver 등록
        val rootView = window.decorView.rootView
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.height
            val keyboardHeight = screenHeight - rect.bottom

            if (keyboardHeight > screenHeight * 0.15) {
                // 키보드가 올라왔을 때의 동작
                binding.mainBottomNavigation.visibility = View.GONE
            } else {
                // 키보드가 사라졌을 때의 동작
                binding.mainBottomNavigation.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // ViewTreeObserver 리스너 제거
        val rootView = window.decorView.rootView
        rootView.viewTreeObserver.removeOnGlobalLayoutListener { }
    }
}