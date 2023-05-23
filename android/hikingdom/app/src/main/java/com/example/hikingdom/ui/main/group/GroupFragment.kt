package com.example.hikingdom.ui.main.group

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.hikingdom.ApplicationClass.Companion.GROUP_WEB_URL
import com.example.hikingdom.ApplicationClass.Companion.NONE_GROUP_WEB_URL
import com.example.hikingdom.config.WebInterface
import com.example.hikingdom.data.local.AppDatabase
import com.example.hikingdom.databinding.FragmentGroupBinding
import com.example.hikingdom.ui.BaseFragment
import com.example.hikingdom.utils.getAccessToken
import com.example.hikingdom.utils.getRefreshToken

class GroupFragment(): BaseFragment<FragmentGroupBinding>(FragmentGroupBinding::inflate) {

    private lateinit var mFilePathCallback: ValueCallback<Array<Uri>>
    private lateinit var fileChooserLauncher: ActivityResultLauncher<Intent>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fileChooserLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                data?.let {
                    val clipData = data.clipData
                    if (clipData != null) {
                        val count = clipData.itemCount
                        val uris = Array<Uri>(count) { i -> clipData.getItemAt(i).uri }
                        mFilePathCallback.onReceiveValue(uris)
                    } else {
                        val uri = data.data
                        if(uri != null){
                            mFilePathCallback.onReceiveValue(arrayOf(uri!!))
                        }else{
                            Log.d("fileChooserLauncher", "uri is null")
                        }
                    }
                }
            }
        }

        // Room에서 사용자 정보 읽어오기
        var user = db?.userDao()?.getUser()

        Log.d("db", "$db")
        Log.d("user", "$user")

        // 사용자가 가입한 그룹이 있으면, 그룹 페이지로 이동
        // 없으면, 그룹 없음 페이지로 이동
        if (user?.clubId != null) {
            webViewSettingWithFileUpload(activityContext, binding.groupWebview, GROUP_WEB_URL)
        } else {
            webViewSetting(binding.groupWebview, NONE_GROUP_WEB_URL)
        }

        swipeReloadWebview(binding.swipeRefreshLayout, binding.groupWebview)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun initAfterBinding() {
    }

    companion object {
        fun newInstance(): GroupFragment = GroupFragment()
    }

    fun webViewSettingWithFileUpload(context: Activity, webView: WebView, url: String){
        var refreshToken = getRefreshToken() // sharedPreference에서 refresh token 가져오기
        var accessToken = getAccessToken() // sharedPreference에서 access token 가져오기

        // 웹뷰 설정
        webView.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(webView: WebView, filePathCallback: ValueCallback<Array<Uri>>, fileChooserParams: FileChooserParams): Boolean {
                mFilePathCallback = filePathCallback
                val intent = Intent(Intent.ACTION_GET_CONTENT)

                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/*"

                fileChooserLauncher.launch(Intent.createChooser(intent, "Select picture"))
                return true
            }
        }

        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true   // 웹뷰 자바스크립트 허용
        webView.settings.domStorageEnabled = true   // 웹뷰 로컬 스토리지 허용
        webView.settings.setSupportMultipleWindows(false)   // 윈도우 여러개 사용여부
        webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN    // 컨텐츠사이즈 맞추기
        webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK    // 캐시 허용 여부
        webView.settings.useWideViewPort = true         // wide viewport 사용 여부
        webView.settings.setSupportZoom(false)          // Zoom사용여부
        webView.settings.loadWithOverviewMode = true    // 메타태그 허용 여부
        webView.settings.builtInZoomControls = false    // 화면 확대 축소 허용 여부
        webView.addJavascriptInterface(WebInterface(context), "Kotlin") // 웹뷰와의 인터페이스 연결

        // 웹뷰로 http 리소스 불러 오기 허용
        val webSetting: WebSettings = webView.settings
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        // 페이지가 로드 되었을 때 JWT 토큰을 전달해줌
        webView.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                Log.d("Token", "$refreshToken")
                webView.evaluateJavascript("saveTokens('$accessToken', '$refreshToken')", null)
            }
        })

        webView.loadUrl(url) // 전달받은 url로 웹뷰 연결

        // 뒤로 가기 불가능 시 종료
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    System.exit(0)
                }
            }
        })

    }

}