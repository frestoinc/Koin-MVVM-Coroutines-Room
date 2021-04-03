package com.frestoinc.sampleapp_kotlin.base

import com.frestoinc.sampleapp_kotlin.api.domain.exception.Failure
import com.frestoinc.sampleapp_kotlin.api.domain.response.Either

interface BaseResponse<T> {

    fun onResponse(response: Either<Failure, T>)
}