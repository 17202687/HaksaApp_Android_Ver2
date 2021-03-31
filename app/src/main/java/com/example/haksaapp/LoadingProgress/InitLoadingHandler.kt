package com.example.haksaapp.LoadingProgress

import android.app.Dialog
import android.content.Context
import android.os.Handler
import com.example.haksaapp.R

class InitLoadingHandler(context: Context):ILoadingDialog {

    private var customDialog = Dialog(context, R.style.CustomFullDialog)
    init{
        this.customDialog.setContentView(R.layout.dialog_init_loading)
    }

    override fun showDialog() {
        this.customDialog.show()
    }

    override fun dismissDialog() {
        Handler().postDelayed(Runnable {
            this.customDialog.dismiss()
        }, 600)
    }
}