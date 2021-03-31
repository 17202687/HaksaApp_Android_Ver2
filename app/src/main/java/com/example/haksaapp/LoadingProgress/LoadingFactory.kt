package com.example.haksaapp.LoadingProgress

import android.content.Context
import com.example.haksaapp.Util.LoadingType

class LoadingFactory {
    fun createLoading(loadingType: LoadingType, mContext: Context): ILoadingDialog{
        return when(loadingType){
            LoadingType.InitLoading->{
                InitLoadingHandler(mContext)
            }
            LoadingType.ChangeUrlLoading->{
                ProgressDialogLoadingTimoutHandler.getInstace(UrlChangeProgressDialog(mContext))
            }
        }
    }
}