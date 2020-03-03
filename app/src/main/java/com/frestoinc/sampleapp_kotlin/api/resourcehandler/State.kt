package com.frestoinc.sampleapp_kotlin.api.resourcehandler

/**
 * Created by frestoinc on 29,February,2020 for SampleApp_Kotlin.
 */
sealed class State<T> {

    class Loading<T> : State<T>()
    data class Error<T>(val error: Exception) : State<T>()
    data class Success<T>(var data: T) : State<T>()

    companion object {
        fun <T> loading(): State<T> =
            Loading()

        fun <T> error(error: Exception): State<T> =
            Error(error)

        fun <T> success(data: T): State<T> =
            Success(data)
    }

}