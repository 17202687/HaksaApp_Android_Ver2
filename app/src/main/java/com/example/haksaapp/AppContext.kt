package com.example.haksaapp

import android.app.Application
import android.content.Context

class AppContext : Application() {
    companion object{
        lateinit var instance : AppContext
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}