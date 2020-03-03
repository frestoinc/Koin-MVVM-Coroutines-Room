package com.frestoinc.sampleapp_kotlin.api.resourcehandler

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */

sealed class Resource<out T> {

    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val exception: Exception) : Resource<Nothing>()

    companion object {
        fun <T> success(data: T): Resource<T> =
            Success(data)

        fun error(exception: Exception): Resource<Nothing> =
            Error(exception)
    }
}