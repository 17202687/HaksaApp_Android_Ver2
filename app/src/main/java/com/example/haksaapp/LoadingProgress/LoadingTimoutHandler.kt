package com.example.haksaapp.LoadingProgress

import android.os.Handler

class LoadingTimoutHandler(customProgress : UrlChangeProgressDialog) {

    private lateinit var customProgress : UrlChangeProgressDialog

    init{
        this.customProgress = customProgress
    }

    companion object{
        private var loadingTimer = false
        private var instnce : LoadingTimoutHandler? = null


        fun getInstace(customProgress: UrlChangeProgressDialog): LoadingTimoutHandler =
                instnce ?: synchronized(this) {
                    instnce ?: LoadingTimoutHandler(customProgress).also {
                        instnce = it
                    }
                }
    }

    fun dialogDelayShow(){
        loadingTimer = true
        Handler().postDelayed(Runnable {
            if(loadingTimer) {
                this.customProgress.show()
            }
        }, 1200)
    }

    fun dialogDelayDismiss(){
        loadingTimer = false
        Handler().postDelayed(Runnable {
            this.customProgress.dismiss()
        },500)
    }
}