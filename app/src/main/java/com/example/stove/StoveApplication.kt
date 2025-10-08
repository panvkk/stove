package com.example.stove

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StoveApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("StoveApp", "StoveApplication onCreate.")
    }
}