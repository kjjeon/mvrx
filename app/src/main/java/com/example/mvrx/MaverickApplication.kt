package com.example.mvrx

import android.app.Application
import com.airbnb.mvrx.Mavericks

class MaverickApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initMavericks()
    }

    private fun initMavericks() {
        Mavericks.initialize(this)
    }
}