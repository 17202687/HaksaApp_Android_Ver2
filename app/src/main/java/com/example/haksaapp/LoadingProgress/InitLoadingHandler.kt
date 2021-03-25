package com.example.haksaapp.LoadingProgress

import android.app.Dialog
import android.content.Context
import android.os.Handler
import com.example.haksaapp.R

class InitLoadingHandler private constructor(context: Context) {

    private var customDialog = Dialog(context, R.style.CustomFullDialog)
    init{
        this.customDialog.setContentView(R.layout.dialog_init_loading)
    }

    companion object{
        @Volatile private var instace: InitLoadingHandler? = null

        fun getInstace(context: Context): InitLoadingHandler =
                instace ?: synchronized(this){
                    instace ?: InitLoadingHandler(context).also{
                        instace = it
                    }
                }
    }

    fun dialogShow() {
        this.customDialog.show()
    }

    fun dialogDismiss(){
        Handler().postDelayed(Runnable {
            this.customDialog.dismiss()
        }, 600)
    }
}