package com.frestoinc.sampleapp_kotlin.api

import okhttp3.Headers
import okhttp3.Request
import okio.Timeout
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

sealed class ApiResponse<out T> {
    data class ApiSuccess<T>(val data: T, val headers: Headers) : ApiResponse<T>()
    data class ApiFailure(val t: Throwable) : ApiResponse<Nothing>()
}

abstract class CallDelegate<TIn, TOut>(
    protected val proxy: Call<TIn>
) : Call<TOut> {
    override fun execute(): Response<TOut> = throw NotImplementedError()
    final override fun enqueue(callback: Callback<TOut>) = enqueueImpl(callback)
    final override fun clone(): Call<TOut> = cloneImpl()

    override fun cancel() = proxy.cancel()
    override fun request(): Request = proxy.request()
    override fun isExecuted() = proxy.isExecuted
    override fun isCanceled() = proxy.isCanceled

    abstract fun enqueueImpl(callback: Callback<TOut>)
    abstract fun cloneImpl(): Call<TOut>
}

class ApiCall<T>(proxy: Call<T>) : CallDelegate<T, ApiResponse<T>>(proxy) {
    override fun enqueueImpl(callback: Callback<ApiResponse<T>>) =
        proxy.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                when {
                    response.isSuccessful -> response.body()?.let {
                        callback.onResponse(
                            this@ApiCall,
                            Response.success(ApiResponse.ApiSuccess(it, response.headers()))
                        )
                    } ?: onFailure(call, Throwable("Body Is Empty"))
                    else -> try {
                        response.errorBody()!!.string().also {
                            callback.onResponse(
                                this@ApiCall,
                                Response.success(ApiResponse.ApiFailure(Exception(it)))
                            )
                        }
                    } catch (e: Exception) {
                        onFailure(call, e)
                    }
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                t.printStackTrace()
                callback.onResponse(this@ApiCall, Response.success(ApiResponse.ApiFailure(t)))
            }
        })

    override fun cloneImpl() = ApiCall(proxy.clone())
    override fun timeout(): Timeout = proxy.timeout()

}

class ApiAdapter(
    private val type: Type
) : CallAdapter<Type, Call<ApiResponse<Type>>> {
    override fun responseType() = type
    override fun adapt(call: Call<Type>): Call<ApiResponse<Type>> = ApiCall(call)
}

class ApiCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ) = when (getRawType(returnType)) {
        Call::class.java -> {
            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
            when (getRawType(callType)) {
                ApiResponse::class.java -> {
                    val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                    ApiAdapter(resultType)
                }
                else -> null
            }
        }
        else -> null
    }
}
