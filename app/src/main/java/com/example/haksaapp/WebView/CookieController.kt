package com.example.haksaapp.WebView

import android.content.Context
import android.util.Log
import android.webkit.CookieManager
import com.example.haksaapp.Util.HttpUrl.CURRENT_URL
import com.example.haksaapp.Util.PreferencesManager
import com.example.haksaapp.Util.Utility.TAG
import okhttp3.OkHttpClient
import okhttp3.RequestBody

class CookieController {
    private val cookieManager = CookieManager.getInstance()
    private val preferencesManager = PreferencesManager()

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
                   val okhttpClient = OkHttpClient()
                   }
           }
       }
    }
}