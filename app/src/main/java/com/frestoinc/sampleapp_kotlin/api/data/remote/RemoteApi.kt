package com.frestoinc.sampleapp_kotlin.api.data.remote

import com.frestoinc.sampleapp_kotlin.api.data.model.Repo
import com.frestoinc.sampleapp_kotlin.api.domain.extension.retrofitField
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */
interface RemoteApi {

    @GET(retrofitField)
    suspend fun getRepositoriesAsync(): Response<List<Repo>>
}