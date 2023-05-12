package com.example.hikingdom.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.hikingdom.utils.Inflate

// Base Fatgment: ActivityBinding::inflate을 전달받아 ViewBinding 생성
abstract class BaseFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Fragment의 ViewBinding
        _binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initAfterBinding()
    }

    // onStart 이 후 실행될 함수
    protected abstract fun initAfterBinding()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Toast 메시지 띄우는 함수
    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}