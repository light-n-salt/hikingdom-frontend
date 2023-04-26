package com.example.hikingdom.ui.main.mypage

import com.example.hikingdom.databinding.FragmentMypageBinding
import com.example.hikingdom.ui.BaseFragment

class MypageFragment(): BaseFragment<FragmentMypageBinding>(FragmentMypageBinding::inflate) {

    override fun initAfterBinding() {

    }

    companion object {
        fun newInstance(): MypageFragment = MypageFragment()
    }
}