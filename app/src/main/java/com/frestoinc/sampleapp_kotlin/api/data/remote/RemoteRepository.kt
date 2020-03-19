package com.frestoinc.sampleapp_kotlin.api.data.remote

import com.frestoinc.sampleapp_kotlin.BuildConfig
import com.frestoinc.sampleapp_kotlin.api.data.model.Repo
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.Repository
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.State


/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */

interface RemoteRepository :
    Repository {

    suspend fun getRemoteRepository(): State<List<Repo>>
}

class RemoteRepositoryImpl(
    private val remoteApi: RemoteApi
) : RemoteRepository {

    override suspend fun getRemoteRepository(): State<List<Repo>> {
        if (BuildConfig.DEBUG) {
            println(remoteApi.hashCode())
        }
        return requestAwait { remoteApi.getRepositoriesAsync() }
    }
}