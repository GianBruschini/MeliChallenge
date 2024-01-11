package com.meli.melichallenge

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MeliChallengeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}