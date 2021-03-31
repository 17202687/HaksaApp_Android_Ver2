package com.example.haksaapp.LoadingProgress

import android.os.Handler

class ProgressDialogLoadingTimoutHandler(private var customProgress: UrlChangeProgressDialog):ILoadingDialog {

    companion object{
        private var loadingTimer = false
        private var instnce : ProgressDialogLoadingTimoutHandler? = null


        fun getInstace(customProgress: UrlChangeProgressDialog): ProgressDialogLoadingTimoutHandler =
                instnce ?: synchronized(this) {
                    instnce ?: ProgressDialogLoadingTimoutHandler(customProgress).also {
                        instnce = it
                    }
                }
    }

    override fun showDialog() {
        loadingTimer = true
        Handler().postDelayed(Runnable {
            if(loadingTimer) {
                this.customProgress.show()
            }
        }, 1200)
    }

    override fun dismissDialog() {
        loadingTimer = false
        Handler().postDelayed(Runnable {
            this.customProgress.dismiss()
        },500)
    }

}