package com.example.hikingdom.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.hikingdom.ApplicationClass
import com.example.hikingdom.data.local.AppDatabase

// Base Acitivity: ActivityBinding::inflate을 전달받아 ViewBinding 생성
abstract class BaseActivity<VB: ViewBinding>(private val inflate: (LayoutInflater) -> VB): AppCompatActivity(){
    protected lateinit var binding: VB // ViewBinding 변수
        private set // setter을 private하게 설정

    private var imm : InputMethodManager? = null // 키보드 동작을 제어하는 매니저 클래스
    private var isDouble = false // 뒤로가기 두번 연속으로 눌러서 종료

    var db: AppDatabase? = null // 데이터베이스

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding 할당
        binding = inflate(layoutInflater)
        setContentView(binding.root)

        // 키보드 동작을 제어하는 매니저 클래스 할당
        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?

        // 리액트 웹뷰 디버깅
        WebView.setWebContentsDebuggingEnabled(true)

        db = AppDatabase.getInstance(this) // 데이터베이스

        // 상속받는 클래스에서 정의하는 abstract func
        initAfterBinding()
    }

    // ViewBinding훗 실행할 함수
    protected abstract fun initAfterBinding()

    // 2초 내에 뒤로가기를 연속으로 눌러서 종료
    override fun onBackPressed() {
        if (isDouble == true) {
            finish()
        }
        isDouble = true
        Toast.makeText(this, "뒤로가기 버튼을 한 번 더 눌러 종료하세요", Toast.LENGTH_LONG).show()
        Handler().postDelayed({
            isDouble = false
        }, 2000) // 시간 제한
    }

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