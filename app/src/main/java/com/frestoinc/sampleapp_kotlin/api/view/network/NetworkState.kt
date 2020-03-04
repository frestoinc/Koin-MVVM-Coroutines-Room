package com.frestoinc.sampleapp_kotlin.api.view.network

/**
 * Created by frestoinc on 04,March,2020 for SampleApp_Kotlin.
 */

enum class NetState {
    SUCCESS, ERROR
}

interface NetworkState {

    fun getState(): NetState
}