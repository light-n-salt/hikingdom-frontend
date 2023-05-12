package com.example.hikingdom.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

// Base Acitivity: ActivityBinding::inflate을 전달받아 ViewBinding 생성
abstract class BaseActivity<VB: ViewBinding>(private val inflate: (LayoutInflater) -> VB): AppCompatActivity(){
    protected lateinit var binding: VB // ViewBinding 변수
        private set // setter을 private하게 설정

    private var imm : InputMethodManager? = null // 키보드 동작을 제어하는 매니저 클래스

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding 할당
        binding = inflate(layoutInflater)
        setContentView(binding.root)

        // 키보드 동작을 제어하는 매니저 클래스 할당
        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?

        // 상속받는 클래스에서 정의하는 abstract func
        initAfterBinding()
    }

    // ViewBinding훗 실행할 함수
    protected abstract fun initAfterBinding()

    // Toast 메시지를 띄우는 함수
    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Activity 전환 함수
    fun startNextActivity(activity: Class<*>?) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }

    // Activity 전환 및 이전 Activity 종료 함수
    fun startActivityWithClear(activity: Class<*>?) {
        val intent = Intent(this, activity)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    // 키보드 숨기는 함수
    fun hideKeyboard(v: View){
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }
}