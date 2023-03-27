package com.wallet.rippleflow

import android.app.Application
import timber.log.Timber

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Timber.i("HCE version ${BuildConfig.VERSION_NAME} is starting")
    }
}