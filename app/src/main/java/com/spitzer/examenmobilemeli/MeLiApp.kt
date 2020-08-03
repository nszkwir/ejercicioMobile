package com.spitzer.examenmobilemeli

import android.app.Application
import android.content.res.Configuration

class MeLiApp: Application() {

    // TODO: 8/2/2020 SE DEFINIRÁ EN CASO QUE SEA NECESARIO

    override fun onCreate() {
        super.onCreate()
    }

    override fun onConfigurationChanged ( newConfig : Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }
}