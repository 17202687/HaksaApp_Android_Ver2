package com.example.haksaapp

import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.haksaapp.Util.HttpUrl.BASE_URL
import com.example.haksaapp.databinding.ActivityMainBinding

class WebViewSetting {

    private lateinit var binding : ActivityMainBinding
    private var currentUrl = BASE_URL

    public fun initBinding(binding: ActivityMainBinding){
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
                if (url != null) {
                    currentUrl = url
                }
                view?.loadUrl(currentUrl)
                return true
            }


        }

        binding.mainWebView.loadUrl(currentUrl)
    }
}