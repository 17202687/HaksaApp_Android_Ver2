package com.example.haksaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import com.example.haksaapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WebViewSetting(this).initBinding(binding)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if((keyCode == KeyEvent.KEYCODE_BACK) && binding.mainWebView.canGoBack()){
            binding.mainWebView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}