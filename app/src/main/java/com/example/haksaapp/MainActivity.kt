package com.example.haksaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import com.example.haksaapp.LoadingProgress.InitLoadingHandler
import com.example.haksaapp.databinding.ActivityMainBinding
import com.example.haksaapp.Util.Utility.TAG

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    // 뒤로가기 두번해야 종료 되게 확인
    private var exitCheck = false

    companion object{
        // 최초 실행 확인
        private var checkFirst = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 최초 실행시 사용할 코도
        if(checkFirst) {
            Log.d(TAG, "onCreate: isFinishing")
            val initLoadingDialog = InitLoadingHandler.getInstace(this)
            initLoadingDialog.dialogShow()
            checkFirst = false
        }

        WebViewSetting(this).initBinding(binding)
        Log.d(TAG, "onCreate: ${AppContext.instance}")
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