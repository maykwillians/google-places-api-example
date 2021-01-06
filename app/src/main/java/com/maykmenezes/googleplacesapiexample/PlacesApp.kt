package com.maykmenezes.googleplacesapiexample

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.KoinContextHandler
import org.koin.core.context.startKoin

class PlacesApp: Application() {
    override fun onCreate() {
        super.onCreate()
        if(KoinContextHandler.getOrNull() == null) {
            startKoin {
                androidContext(this@PlacesApp)
            }
        }
    }
}