package com.example.hikingdom.ui.main.group

import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import com.example.hikingdom.ApplicationClass.Companion.GROUP_WEB_URL
import com.example.hikingdom.ApplicationClass.Companion.NONE_GROUP_WEB_URL
import com.example.hikingdom.data.local.AppDatabase
import com.example.hikingdom.databinding.FragmentGroupBinding
import com.example.hikingdom.ui.BaseFragment

class GroupFragment(): BaseFragment<FragmentGroupBinding>(FragmentGroupBinding::inflate) {

    override fun initAfterBinding() {
        // Room에서 사용자 정보 읽어오기
        var user = db?.userDao()?.getUser()

        // 사용자가 가입한 그룹이 있으면, 그룹 페이지로 이동
        // 없으면, 그룹 없음 페이지로 이동
        if (user?.clubId as Boolean) {
            webViewSetting(activityContext, binding.groupWebview, GROUP_WEB_URL)
        } else {
            webViewSetting(activityContext, binding.groupWebview, NONE_GROUP_WEB_URL)
        }
    }

    companion object {
        fun newInstance(): GroupFragment = GroupFragment()
    }

}