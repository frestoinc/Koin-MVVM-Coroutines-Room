package com.frestoinc.sampleapp_kotlin.api.data.remote

import com.frestoinc.sampleapp_kotlin.api.data.model.Repo
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */
interface RemoteApi {

    @GET(retrofitField)
    fun getRepositoriesAsync(): Deferred<List<Repo>>
}