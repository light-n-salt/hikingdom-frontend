package com.example.hikingdom.ui.main.group

import com.example.hikingdom.databinding.FragmentGroupBinding
import com.example.hikingdom.ui.BaseFragment

class GroupFragment(): BaseFragment<FragmentGroupBinding>(FragmentGroupBinding::inflate) {

    override fun initAfterBinding() {

    }

    companion object {
        fun newInstance(): GroupFragment = GroupFragment()
    }
}