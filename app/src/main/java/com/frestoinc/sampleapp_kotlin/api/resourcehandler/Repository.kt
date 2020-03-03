package com.frestoinc.sampleapp_kotlin.api.resourcehandler

import kotlinx.coroutines.Deferred
import timber.log.Timber

/**
 * Created by frestoinc on 29,February,2020 for SampleApp_Kotlin.
 */
interface Repository {

    suspend fun <T> requestAwait(
        call: () -> Deferred<T>
    ): Resource<T> {
        return try {
            val result = call().await()
            Resource.success(result)
        } catch (exception: Exception) {
            Timber.e(exception)
            Resource.error(exception)
        }
    }

    suspend fun <T> request(
        call: suspend () -> T
    ): Resource<T> {
        return try {
            Resource.success(call())
        } catch (exception: Exception) {
            Timber.e(exception)
            Resource.error(exception)
        }
    }
}