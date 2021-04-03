package com.frestoinc.sampleapp_kotlin

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.frestoinc.sampleapp_kotlin.service.CustomWorker
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.concurrent.TimeUnit

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
        //setupPeriodicWork()
    }

    private fun setupPeriodicWork() {
        WorkManager.getInstance(this).enqueue(workRequest)
    }

    private val workRequest: PeriodicWorkRequest = PeriodicWorkRequest.Builder(
        CustomWorker::class.java, 4, TimeUnit.HOURS
    ).apply {
        setConstraints(Constraints.Builder().apply {
            setRequiredNetworkType(NetworkType.CONNECTED)
        }.build())
        setInitialDelay(0, TimeUnit.SECONDS)
    }.build()

}