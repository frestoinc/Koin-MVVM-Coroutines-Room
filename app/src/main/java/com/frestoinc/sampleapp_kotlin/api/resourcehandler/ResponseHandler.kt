package com.frestoinc.sampleapp_kotlin.api.resourcehandler

import com.frestoinc.sampleapp_kotlin.api.state.Resource
import retrofit2.HttpException
import java.net.SocketTimeoutException

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */

enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1)
}

open class ResponseHandler {
    fun <T : Any> onSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    fun <T : Any> onException(exception: Exception): Resource<T> {
        return when (exception) {
            is HttpException -> Resource.error(getErrorMessage(exception.code()), null)
            is SocketTimeoutException -> Resource.error(
                getErrorMessage(ErrorCodes.SocketTimeOut.code),
                null
            )
            else -> Resource.error(getErrorMessage(Int.MAX_VALUE), null)
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            ErrorCodes.SocketTimeOut.code -> "Timeout"
            401 -> "401: Unauthorised"
            404 -> "404: Not found"
            else -> "$code: Something went wrong"
        }
    }
}