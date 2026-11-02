package com.infinity.browser

import android.app.Application

class InfinityApp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: InfinityApp
            private set
    }
}
