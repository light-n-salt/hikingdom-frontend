package com.example.hikingdom.ui.main.mypage

import android.util.Log
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import com.example.hikingdom.ApplicationClass
import com.example.hikingdom.ApplicationClass.Companion.MYPAGE_WEB_URL
import com.example.hikingdom.data.local.AppDatabase
import com.example.hikingdom.databinding.FragmentMypageBinding
import com.example.hikingdom.ui.BaseFragment

class MypageFragment(): BaseFragment<FragmentMypageBinding>(FragmentMypageBinding::inflate) {

    override fun initAfterBinding() {
        // Room에서 사용자 정보 읽어오기
        var user = db?.userDao()?.getUser()

        // 사용자 닉네임이 해당하는 프로필 페이지로 이동
        webViewSetting(binding.mypageWebview,MYPAGE_WEB_URL + '/' + user?.nickname)
    }

    companion object {
        fun newInstance(): MypageFragment = MypageFragment()
    }
}