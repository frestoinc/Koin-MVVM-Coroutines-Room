package com.frestoinc.sampleapp_kotlin.api.domain.base

import com.frestoinc.sampleapp_kotlin.api.domain.exception.Failure
import com.frestoinc.sampleapp_kotlin.api.domain.response.Either

/**
 * Created by frestoinc on 22,April,2020 for SampleApp_Kotlin.
 */
interface BaseResponse<T> {

    fun onResponse(response: Either<Failure, T>)
}