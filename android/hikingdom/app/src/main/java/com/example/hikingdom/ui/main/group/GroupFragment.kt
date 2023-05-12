package com.example.hikingdom.ui.main.group

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