package com.example.haksaapp.WebView

import android.util.Log
import android.webkit.CookieManager
import com.example.haksaapp.Util.HttpUrl.CURRENT_URL
import com.example.haksaapp.Util.Utility.TAG

class CookieController {
    val cookieManager = CookieManager.getInstance()

    companion object{
        private var instnce : CookieController? = null


        fun getInstace(): CookieController =
                instnce ?: synchronized(this) {
                    instnce ?: CookieController().also {
                        instnce = it
                    }
                }
    }

    fun AddCookie(cookieName: String, cookieDocumente : String){
        cookieManager.setCookie(CURRENT_URL, cookieName + "=" + cookieDocumente)
    }

    //쿠키 Log
    fun Print_Cookie (url : String?){
        Log.d(TAG, "CookieController - Print_Cookie()")
        val cookies : String? = cookieManager.getCookie(url)
        var parsing_cookies : HashMap<String, String>
        parsing_cookies = ParsingCookie(cookies!!)
        for(parsing_cookie in parsing_cookies.keys){
            Log.d(TAG, parsing_cookie)
        }
    }

    //쿠키를 decoed해서 HashMap으로 반환
    fun ParsingCookie (cookies : String) : HashMap<String, String>{
        val cookie_split = cookies.split(";")
        val parsing_cookies : HashMap<String, String> = hashMapOf("" to "")
        for(cookie in cookie_split){
            val Argument = cookie.split("=")
            parsing_cookies.put(Argument[0].replace(" ", ""), Argument[1].replace(" ", ""))
        }
        return parsing_cookies
    }
}