package com.frestoinc.sampleapp_kotlin.api.data.remote

import com.frestoinc.sampleapp_kotlin.api.resourcehandler.Resource
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.State

/**
 * Created by frestoinc on 28,February,2020 for SampleApp_Kotlin.
 */
const val baseURL: String = "https://github-trending-api.now.sh/"
const val retrofitField: String = "repositories"
const val roomDB: String = "repo"

fun <T> Resource<T>.toState(): State<T?> {
    return when (this) {
        is Resource.Success -> State.success(data)
        is Resource.Error -> State.error(exception)
    }
}

const val error: String = "Error"