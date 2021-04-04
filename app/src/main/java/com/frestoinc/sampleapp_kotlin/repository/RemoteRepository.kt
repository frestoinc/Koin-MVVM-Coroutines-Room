package com.frestoinc.sampleapp_kotlin.repository

import com.frestoinc.sampleapp_kotlin.api.ApiResponse
import com.frestoinc.sampleapp_kotlin.api.trending_api.TrendingService
import com.frestoinc.sampleapp_kotlin.models.Response
import com.frestoinc.sampleapp_kotlin.models.trending_api.TrendingEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface IRemoteRepository {

    suspend fun getTrendingFromRemote(): Response<List<TrendingEntity>>
}

class RemoteRepository @Inject constructor(private val trendingService: TrendingService) :
    IRemoteRepository {

    override suspend fun getTrendingFromRemote(): Response<List<TrendingEntity>> =
        withContext(Dispatchers.IO) {
            when (val response = trendingService.getRepositoriesAsync()) {
                is ApiResponse.ApiSuccess -> Response.Success(response.data)
                is ApiResponse.ApiFailure -> Response.Error(response.t)
            }
        }

}