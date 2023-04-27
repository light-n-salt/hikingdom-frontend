package com.example.hikingdom.ui.main.hiking

import android.content.Intent
import android.view.View
import com.example.hikingdom.BuildConfig
import com.example.hikingdom.databinding.FragmentHikingBinding
import com.example.hikingdom.ui.BaseFragment
import com.example.hikingdom.ui.main.group.GroupFragment

class HikingFragment(): BaseFragment<FragmentHikingBinding>(FragmentHikingBinding::inflate) {

    override fun initAfterBinding() {
        setHikingService()
    }

    companion object {
        fun newInstance(): GroupFragment = GroupFragment()
        const val  ACTION_STOP = "${BuildConfig.APPLICATION_ID}.stop"
    }

    fun setHikingService(){
        val hikingStartBtn = binding.hikingStartBtn
        val hikingFinishBtn = binding.hikingFinishBtn
        hikingStartBtn.setOnClickListener {
            val startServiceIntent = Intent(activity, HikingForegroundService::class.java)
            activity?.startService(startServiceIntent)
            showToast("등산 기록을 시작합니다.")
            hikingFinishBtn.visibility = View.VISIBLE
            hikingStartBtn.visibility = View.GONE
        }

        hikingFinishBtn.setOnClickListener {
            val stopServiceIntent = Intent(activity, HikingForegroundService::class.java)
            stopServiceIntent.action = ACTION_STOP
            activity?.startService(stopServiceIntent)
            showToast("등산 기록을 종료합니다.")
            hikingFinishBtn.visibility = View.GONE
            hikingStartBtn.visibility = View.VISIBLE
        }
    }

}