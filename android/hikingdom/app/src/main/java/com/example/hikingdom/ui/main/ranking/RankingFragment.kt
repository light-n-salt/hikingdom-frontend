package com.example.hikingdom.ui.main.ranking

import android.os.Build
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import com.example.hikingdom.ApplicationClass.Companion.RANKING_WEB_URL
import com.example.hikingdom.databinding.FragmentRankingBinding
import com.example.hikingdom.ui.BaseFragment

class RankingFragment(): BaseFragment<FragmentRankingBinding>(FragmentRankingBinding::inflate) {

    override fun initAfterBinding() {
        webViewSetting(binding.rankingWebview, RANKING_WEB_URL)
        swipeReloadWebview(binding.swipeRefreshLayout, binding.rankingWebview)
    }

    companion object {
        fun newInstance(): RankingFragment = RankingFragment()
    }

}