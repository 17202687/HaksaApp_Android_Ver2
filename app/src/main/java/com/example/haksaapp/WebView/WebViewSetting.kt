package com.example.haksaapp.WebView

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.webkit.*
import com.example.haksaapp.Firebase.MyFirebaseMessagingService
import com.example.haksaapp.LoadingProgress.LoadingFactory
import com.example.haksaapp.Util.HttpUrl.BASE_URL
import com.example.haksaapp.Util.HttpUrl.CURRENT_URL
import com.example.haksaapp.Util.LoadingType
import com.example.haksaapp.Util.PreferencesManager
import com.example.haksaapp.Util.Utility.TAG
import com.example.haksaapp.databinding.ActivityMainBinding

class WebViewSetting(context: Context){

    private lateinit var binding : ActivityMainBinding
    private var mContext: Context = context
    private val cookieController = CookieController.getInstace()
    private val preferencesManager = PreferencesManager()
    val initLoading = LoadingFactory().createLoading(LoadingType.InitLoading, mContext)
    val loadingTimoutHandler = LoadingFactory().createLoading(LoadingType.ChangeUrlLoading, mContext)

    @SuppressLint("SetJavaScriptEnabled")
    fun initBinding(binding: ActivityMainBinding){

       // 현재 모바일 데이터 서버 전송
        cookieController.addCookie( "DeviceModel",Build.MODEL.toString())
        cookieController.addCookie("DeviceVersion","Android_" + Build.VERSION.RELEASE)
        MyFirebaseMessagingService().getToken {
            cookieController.addCookie( "FBTOK",it)
        }

        initLoading.showDialog()

        this.binding = binding
        binding.mainWebView.settings.apply {
            javaScriptEnabled = true                        // 자바스크립트 실행 허용
            javaScriptCanOpenWindowsAutomatically = false   // 자바스크립트에서 새창 실 행 거부
            setSupportMultipleWindows(false)                // 새 창 실행 거
            loadWithOverviewMode = true                     // 메타 태그 허용

            useWideViewPort = true                          // 화면 사이즈 맞추기 허용
            setSupportZoom(false)                           // 화면 줌 허용
            builtInZoomControls = false                     // 화면 확대 축소 허용 여부

            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK // 브라우저 캐시 허용 여부
            domStorageEnabled = true                        // 로컬저장소 허용

            cacheMode = WebSettings.LOAD_DEFAULT            //캐시 설정
        }


        binding.mainWebView.webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                cookieController.mainCookieHandler(mContext)
                cookieController.print_Cookie()

                if (url != null) {
                    CURRENT_URL = url
                }

                loadingTimoutHandler.showDialog()
                view?.loadUrl(CURRENT_URL)
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                loadingTimoutHandler.dismissDialog()
                initLoading.dismissDialog()
            }
        }

        binding.mainWebView.loadUrl(CURRENT_URL)
    }
}