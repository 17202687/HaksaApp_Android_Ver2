package com.example.haksaapp

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.webkit.*
import com.example.haksaapp.LoadingProgress.UrlChangeProgressDialog
import com.example.haksaapp.Util.AppContext
import com.example.haksaapp.Util.HttpUrl.CURRENT_URL
import com.example.haksaapp.databinding.ActivityMainBinding

class WebViewSetting(context: Context){

    private lateinit var binding : ActivityMainBinding
    private lateinit var mContext: Context

    init {
        this.mContext = context
    }

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

        val customProgress = UrlChangeProgressDialog(context = mContext)

        binding.mainWebView.webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                customProgress.show()
                if (url != null) {
                    CURRENT_URL = url
                }
                view?.loadUrl(CURRENT_URL)
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                customProgress.dismiss()
            }
        }



        binding.mainWebView.loadUrl(CURRENT_URL)
    }
}