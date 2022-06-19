package com.app.celda

import android.app.Application
import com.chibatching.kotpref.Kotpref

class CeldaApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Kotpref.init(this)
    }
}