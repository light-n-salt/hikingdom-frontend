package com.example.hikingdom.ui.main.ranking

import com.example.hikingdom.databinding.FragmentRankingBinding
import com.example.hikingdom.ui.BaseFragment

class RankingFragment(): BaseFragment<FragmentRankingBinding>(FragmentRankingBinding::inflate) {

    override fun initAfterBinding() {

    }

    companion object {
        fun newInstance(): RankingFragment = RankingFragment()
    }
}