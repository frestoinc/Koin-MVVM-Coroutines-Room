package com.frestoinc.sampleapp_kotlin.api.trending_api

import com.frestoinc.sampleapp_kotlin.models.trending_api.TrendingEntity
import retrofit2.Response
import retrofit2.http.GET

interface TrendingService {

    @GET("repositories")
    suspend fun getRepositoriesAsync(): Response<List<TrendingEntity>>
}