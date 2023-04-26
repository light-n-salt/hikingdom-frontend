package com.example.hikingdom.ui.main.home

import com.example.hikingdom.databinding.FragmentHomeBinding
import com.example.hikingdom.ui.BaseFragment

class HomeFragment(): BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun initAfterBinding() {

    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}