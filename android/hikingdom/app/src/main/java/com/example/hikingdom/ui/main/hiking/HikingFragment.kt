package com.example.hikingdom.ui.main.hiking

import com.example.hikingdom.databinding.FragmentHikingBinding
import com.example.hikingdom.ui.BaseFragment
import com.example.hikingdom.ui.main.group.GroupFragment

class HikingFragment(): BaseFragment<FragmentHikingBinding>(FragmentHikingBinding::inflate) {

    override fun initAfterBinding() {

    }

    companion object {
        fun newInstance(): GroupFragment = GroupFragment()
    }
}