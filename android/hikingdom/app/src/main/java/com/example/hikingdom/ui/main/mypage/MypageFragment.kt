package com.example.hikingdom.ui.main.mypage

import android.os.Build
import android.webkit.WebSettings
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
        webView.settings.domStorageEnabled = true

        val wsetting: WebSettings = webView.settings
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // https 이미지.
            wsetting.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    System.exit(0)
                }
            }
        })

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