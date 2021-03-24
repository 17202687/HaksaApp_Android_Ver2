package com.example.haksaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.widget.Toast
import com.example.haksaapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    // 뒤로가기 두번해야 종료 되게 확인
    private var exitCheck = false

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
        if(!exitCheck){
            exitCheck = true
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            Handler().postDelayed(Runnable { exitCheck = false }, 1000)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}