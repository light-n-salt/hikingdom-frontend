package com.example.hikingdom.ui.main.ranking

import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import com.example.hikingdom.ApplicationClass.Companion.RANKING_WEB_URL
import com.example.hikingdom.databinding.FragmentRankingBinding
import com.example.hikingdom.ui.BaseFragment

class RankingFragment(): BaseFragment<FragmentRankingBinding>(FragmentRankingBinding::inflate) {

    override fun initAfterBinding() {
        webViewSetting()
    }

    companion object {
        fun newInstance(): RankingFragment = RankingFragment()
    }

    fun webViewSetting(){
        val webView = binding.rankingWebview
        webView.webViewClient = WebViewClient()
        webView.loadUrl(RANKING_WEB_URL)
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