package com.frestoinc.sampleapp_kotlin.api.domain.response

/**
 * Created by frestoinc on 23,April,2020 for SampleApp_Kotlin.
 */
sealed class State<T>(
    val data: T? = null,
    val ex: Exception? = null
) {
    class Loading<T> : State<T>()
    class Success<T>(data: T) : State<T>(data)
    class Error<T>(ex: Exception, data: T? = null) : State<T>(data, ex)
}
