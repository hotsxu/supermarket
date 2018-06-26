package com.pemt.market.app

import android.app.Application

class MyApp : Application() {

    companion object {
        lateinit var App: MyApp
    }

    override fun onCreate() {
        super.onCreate()
        App = this
    }
}