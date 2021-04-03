package com.frestoinc.sampleapp_kotlin.repository

import com.frestoinc.sampleapp_kotlin.api.domain.response.State
import com.frestoinc.sampleapp_kotlin.api.trending_api.TrendingService
import com.frestoinc.sampleapp_kotlin.models.trending_api.TrendingEntity
import javax.inject.Inject

interface IRemoteRepository {

    suspend fun getTrendingFromRemote(): State<List<TrendingEntity>>
}

class RemoteRepository @Inject constructor(private val trendingService: TrendingService) :
    IRemoteRepository {

    override suspend fun getTrendingFromRemote(): State<List<TrendingEntity>> {
        val response = trendingService.getRepositoriesAsync()
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