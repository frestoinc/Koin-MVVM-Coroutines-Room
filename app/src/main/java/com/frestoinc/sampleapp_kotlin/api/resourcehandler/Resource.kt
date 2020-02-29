package com.frestoinc.sampleapp_kotlin.api.resourcehandler

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */

open class Resource<out T>(val resourceStatus: ResourceStatus, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                ResourceStatus.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(
                ResourceStatus.ERROR,
                null,
                msg
            )
        }

        fun <T> loading(): Resource<T> {
            return Resource(
                ResourceStatus.LOADING,
                null,
                null
            )
        }
    }
}