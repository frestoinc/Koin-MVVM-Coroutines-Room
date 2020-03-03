package com.frestoinc.sampleapp_kotlin.api.base

import android.app.Application
import com.frestoinc.sampleapp_kotlin.di.appModule
import com.frestoinc.sampleapp_kotlin.di.dataModule
import com.frestoinc.sampleapp_kotlin.di.networkModule
import com.frestoinc.sampleapp_kotlin.di.roomModule
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
            modules(
                listOf(
                    appModule,
                    networkModule,
                    roomModule,
                    dataModule
                )
            )
        }
    }
}