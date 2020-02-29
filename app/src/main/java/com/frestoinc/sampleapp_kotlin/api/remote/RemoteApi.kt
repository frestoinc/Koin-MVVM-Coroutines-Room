package com.frestoinc.sampleapp_kotlin.api.remote

import com.frestoinc.sampleapp_kotlin.api.model.Repo
import retrofit2.http.GET

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */
interface RemoteApi {

    @GET(retrofitField)
    suspend fun getRepositories(): List<Repo>
}