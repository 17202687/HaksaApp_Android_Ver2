package com.example.haksaapp.LoadingProgress

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.example.haksaapp.R
import com.example.haksaapp.databinding.DialogProgressBinding

public class UrlChangeProgressDialog(context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(DialogProgressBinding.inflate(layoutInflater).root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}
