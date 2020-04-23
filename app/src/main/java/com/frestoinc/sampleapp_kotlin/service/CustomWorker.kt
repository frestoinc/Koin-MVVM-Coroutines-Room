package com.frestoinc.sampleapp_kotlin.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.frestoinc.sampleapp_kotlin.api.data.manager.DataManager
import com.frestoinc.sampleapp_kotlin.api.domain.response.State
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Created by frestoinc on 09,March,2020 for SampleApp_Kotlin.
 */
class CustomWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params),
    KoinComponent {

    private val dataManager: DataManager by inject()

    override suspend fun doWork(): Result {
        return try {
            when (val result = dataManager.getRemoteRepository()) {
                is State.Success -> {
                    dataManager.insert(result.data!!)
                    Result.success()
                }
                else -> Result.failure()
            }
            Result.success()
        } catch (exception: Exception) {
            Result.failure()
        }
    }
}