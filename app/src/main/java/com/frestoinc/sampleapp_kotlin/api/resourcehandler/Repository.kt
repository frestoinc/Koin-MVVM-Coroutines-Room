package com.frestoinc.sampleapp_kotlin.api.resourcehandler

import kotlinx.coroutines.Deferred
import timber.log.Timber

/**
 * Created by frestoinc on 29,February,2020 for SampleApp_Kotlin.
 */
interface Repository {

    suspend fun <T> requestAwait(
        call: () -> Deferred<T>
    ): State<T> {
        return try {
            State.success(call().await())
        } catch (exception: Exception) {
            Timber.e(exception)
            State.error(exception)
        }
    }

    suspend fun <T> request(
        call: suspend () -> T
    ): State<T> {
        return try {
            State.success(call())
        } catch (exception: Exception) {
            Timber.e(exception)
            State.error(exception)
        }
    }
}