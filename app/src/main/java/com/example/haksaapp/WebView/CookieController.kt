package com.example.haksaapp.WebView

import android.content.Context
import android.os.Build
import android.util.Log
import android.webkit.CookieManager
import androidx.annotation.RequiresApi
import com.example.haksaapp.Firebase.MyFirebaseMessagingService
import com.example.haksaapp.Util.HttpUrl.CURRENT_URL
import com.example.haksaapp.Util.PreferencesManager
import com.example.haksaapp.Util.Utility.TAG
import com.example.haksaapp.Util.Utility.URL_SET_FCM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception

class CookieController {
    private val cookieManager = CookieManager.getInstance()
    private val preferencesManager = PreferencesManager()
    private var checkFirst = true

    companion object{
        private var instnce : CookieController? = null

        fun getInstace(): CookieController =
                instnce ?: synchronized(this) {
                    instnce ?: CookieController().also {
                        instnce = it
                    }
                }
    }

    fun addCookie(cookieName: String, cookieDocumente : String){
        cookieManager.setCookie(CURRENT_URL, cookieName + "=" + cookieDocumente)
    }

    //쿠키 Log
    fun print_Cookie () : HashMap<String, String>{
        Log.d(TAG, "CookieController - Print_Cookie()")
        val cookies : String? = cookieManager.getCookie(CURRENT_URL)
        val parsing_cookies : HashMap<String, String>
        parsing_cookies = parsingCookie(cookies!!)
        for(parsing_cookie in parsing_cookies){
            Log.d(TAG, "print_Cookie : " + parsing_cookie.key +"=" + parsing_cookie.value)
        }
        return parsing_cookies
    }

    //쿠키를 decoed해서 HashMap으로 반환
    fun parsingCookie (cookies : String) : HashMap<String, String>{
        val cookie_split = cookies.split(";")
        val parsing_cookies : HashMap<String, String> = hashMapOf("" to "")
        for(cookie in cookie_split){
            val Argument = cookie.split("=")
            parsing_cookies.put(Argument[0].replace(" ", ""), Argument[1].replace(" ", ""))
        }
        return parsing_cookies
    }

    fun mainCookieHandler(context : Context){
        val parsingCookie = parsingCookie(cookieManager.getCookie(CURRENT_URL))
        for(cookie in parsingCookie){
           when(cookie.key){
               "studentID_saveServer" -> preferencesManager.setString(context, "studentID_saveServer", cookie.value)
               "studentID_delete" -> preferencesManager.removeKey(context, "studentID_saveServer")
               "studentID_temp" ->{
                   if(checkFirst) {
                       MyFirebaseMessagingService().getToken {
                           val okhttpClient = OkHttpClient()
                           val requestBody = FormBody.Builder().apply {
                               add("user_id", cookie.value)
                               add("firebase_key", it)
                           }.build()

                           val request = Request.Builder().apply {
                               url(URL_SET_FCM)
                               post(requestBody)
                           }.build()

                           CoroutineScope(Dispatchers.Default).async {
                               try {
                                   val res = okhttpClient.newCall(request).execute()
                               } catch (e: Exception) {
                                   e.printStackTrace()
                               }
                           }
                       }
                       checkFirst = false
                   }
               }
           }
       }
    }

    //모든 쿠키 삭제
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun DeleteCookie_All(){
        Log.d(TAG, "CookieController - DeleteCookie_All()")
        cookieManager.removeAllCookies {  }
    }
}