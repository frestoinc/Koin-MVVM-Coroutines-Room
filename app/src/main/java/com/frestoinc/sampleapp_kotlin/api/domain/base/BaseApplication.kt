package com.frestoinc.sampleapp_kotlin.api.domain.base

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.frestoinc.sampleapp_kotlin.di.appModule
import com.frestoinc.sampleapp_kotlin.di.networkModule
import com.frestoinc.sampleapp_kotlin.di.roomModule
import com.frestoinc.sampleapp_kotlin.service.CustomWorker
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        setupPeriodicWork()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    appModule,
                    roomModule,
                    networkModule
                )
            )
        }
    }

    private fun setupPeriodicWork() {
        WorkManager.getInstance(this).enqueue(workRequest)
    }

    private val constraints: Constraints by lazy {
        return@lazy Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    }

    private val workRequest: PeriodicWorkRequest by lazy {
        return@lazy PeriodicWorkRequest.Builder(
                CustomWorker::class.java, 4, TimeUnit.HOURS
            )
            .setConstraints(constraints)
            .setInitialDelay(0, TimeUnit.SECONDS)
            .build()
    }
}