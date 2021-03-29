package com.example.haksaapp.WebView

import android.content.Context
import android.util.Log
import android.webkit.CookieManager
import com.example.haksaapp.Util.HttpUrl.CURRENT_URL
import com.example.haksaapp.Util.PreferencesManager
import com.example.haksaapp.Util.Utility.TAG

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

    fun AddCookie(cookieName: String, cookieDocumente : String){
        cookieManager.setCookie(CURRENT_URL, cookieName + "=" + cookieDocumente)
    }

    //쿠키 Log
    fun Print_Cookie () : HashMap<String, String>{
        Log.d(TAG, "CookieController - Print_Cookie()")
        val cookies : String? = cookieManager.getCookie(CURRENT_URL)
        val parsing_cookies : HashMap<String, String>
        parsing_cookies = ParsingCookie(cookies!!)
        for(parsing_cookie in parsing_cookies){
            Log.d(TAG, parsing_cookie.key)
        }
        return parsing_cookies
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

    fun MainCookieHandler(context : Context){
        val parsingCookie = ParsingCookie(cookieManager.getCookie(CURRENT_URL))
        for(cookie in parsingCookie){
           when(cookie.key){
               "studentID_saveServer" -> preferencesManager.setString(context, "studentID_saveServer", cookie.value)
               "studentID_delete" -> preferencesManager.removeKey(context, "studentID_saveServer")
           }
        }
    }
}