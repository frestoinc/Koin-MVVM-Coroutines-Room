package com.frestoinc.sampleapp_kotlin.api.data.remote

import com.frestoinc.sampleapp_kotlin.api.data.model.Repo
import com.frestoinc.sampleapp_kotlin.api.domain.response.State


/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */

interface RemoteRepository {

    suspend fun getRemoteRepository(): State<List<Repo>>
}

class RemoteRepositoryImpl(
    private val remoteApi: RemoteApi
) : RemoteRepository {

    override suspend fun getRemoteRepository(): State<List<Repo>> {
        val response = remoteApi.getRepositoriesAsync()
        return try {
            when (response.isSuccessful) {
                true -> State.Success(response.body() ?: emptyList())
                false -> State.Error(Exception(response.message()))
            }

        } catch (ex: Exception) {
            State.Error(ex)
        }
    }
}