package com.frestoinc.sampleapp_kotlin.api.data.remote

import com.frestoinc.sampleapp_kotlin.api.data.model.Repo
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.Repository
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.Resource


/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */

interface RemoteRepository :
    Repository {

    suspend fun getRemoteRepository(): Resource<List<Repo>>
}

class RemoteRepositoryImpl(
    private val remoteApi: RemoteApi
) : RemoteRepository {

    override suspend fun getRemoteRepository(): Resource<List<Repo>> {
        return requestAwait { remoteApi.getRepositoriesAsync() }
    }
}