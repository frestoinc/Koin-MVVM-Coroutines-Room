package com.frestoinc.sampleapp_kotlin.api.remote

import com.frestoinc.sampleapp_kotlin.api.resourcehandler.ResponseHandler
import com.frestoinc.sampleapp_kotlin.api.state.Resource


/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */

interface RemoteRepository {

    suspend fun getRemoteRepository(): Resource<List<Repo>>
}

class RemoteRepositoryImpl(
    private val remoteApi: RemoteApi,
    private val responseHandler: ResponseHandler
) {

    suspend fun getRemoteRepository(): Resource<List<Repo>> {
        return try {
            val result = remoteApi.getRepositories()
            responseHandler.onSuccess(result)
        } catch (exception: Exception) {
            responseHandler.onException(exception)
        }
    }
}