package com.example.hikingdom.ui.main.group

import android.os.Build
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import com.example.hikingdom.ApplicationClass.Companion.GROUP_WEB_URL
import com.example.hikingdom.databinding.FragmentGroupBinding
import com.example.hikingdom.ui.BaseFragment

class GroupFragment(): BaseFragment<FragmentGroupBinding>(FragmentGroupBinding::inflate) {

    override fun initAfterBinding() {
        webViewSetting()
    }

    companion object {
        fun newInstance(): GroupFragment = GroupFragment()
    }

    fun webViewSetting(){
        val webView = binding.groupWebview
        webView.webViewClient = WebViewClient()
        webView.loadUrl(GROUP_WEB_URL)
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