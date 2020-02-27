package com.frestoinc

import android.app.Application
import com.frestoinc.sampleapp_kotlin.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(appModule)
        }
    }
}