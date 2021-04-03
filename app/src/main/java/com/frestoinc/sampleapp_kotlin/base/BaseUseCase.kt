package com.frestoinc.sampleapp_kotlin.base

import com.frestoinc.sampleapp_kotlin.api.domain.exception.Failure
import com.frestoinc.sampleapp_kotlin.api.domain.response.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class BaseUseCase<T, in V> where T : Any {

    abstract suspend fun run(v: V? = null): Either<Failure, T>

    fun invoke(
        scope: CoroutineScope,
        v: V?,
        onResult: BaseResponse<T>
    ) {
        val job = scope.async { run(v) }
        scope.launch {
            job.await().let {
                onResult.onResponse(it)
            }
        }
    }

}