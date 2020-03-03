package com.frestoinc.sampleapp_kotlin.api.resourcehandler

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */

sealed class Resource<T> {


    data class Success<T>(val data: T?) : Resource<T>()
    data class Error<T>(val exception: Exception) : Resource<T>()

    companion object {
        fun <T> success(data: T): Resource<T> =
            Success(data)

        fun <T> error(exception: Exception): Resource<T> =
            Error(exception)
    }
}