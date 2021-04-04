package com.frestoinc.sampleapp_kotlin

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */
@HiltAndroidApp
class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}