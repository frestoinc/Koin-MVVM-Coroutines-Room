package com.frestoinc.sampleapp_kotlin.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.frestoinc.sampleapp_kotlin.api.domain.response.State
import com.frestoinc.sampleapp_kotlin.helpers.DataHelper
import javax.inject.Inject

class CustomWorker @Inject constructor(
    private val dataHelper: DataHelper,
    ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        return try {
            when (val result = dataHelper.getRemoteRepository()) {
                is State.Success -> {
                    dataHelper.insert(result.data!!)
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