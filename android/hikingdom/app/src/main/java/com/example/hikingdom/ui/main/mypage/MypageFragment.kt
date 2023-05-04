package com.example.hikingdom.ui.main.mypage

import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import com.example.hikingdom.ApplicationClass.Companion.MYPAGE_WEB_URL
import com.example.hikingdom.databinding.FragmentMypageBinding
import com.example.hikingdom.ui.BaseFragment

class MypageFragment(): BaseFragment<FragmentMypageBinding>(FragmentMypageBinding::inflate) {

    override fun initAfterBinding() {
        webViewSetting()
    }

    companion object {
        fun newInstance(): MypageFragment = MypageFragment()
    }

    fun webViewSetting(){
        val webView = binding.mypageWebview
        webView.webViewClient = WebViewClient()
        webView.loadUrl(MYPAGE_WEB_URL)
        webView.settings.javaScriptEnabled = true

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    System.exit(0)
                }
            }
        })
    }
}