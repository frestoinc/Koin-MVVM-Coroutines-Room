package com.frestoinc.sampleapp_kotlin.api.remote

import retrofit2.http.GET

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */
interface RemoteApi {

    @GET("repositories")
    suspend fun getRepositories(): List<Repo>
}