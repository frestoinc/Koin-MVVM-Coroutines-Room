package com.frestoinc.sampleapp_kotlin.repository

import com.frestoinc.sampleapp_kotlin.api.ApiResponse
import com.frestoinc.sampleapp_kotlin.api.trending_api.TrendingService
import com.frestoinc.sampleapp_kotlin.domain.Either
import com.frestoinc.sampleapp_kotlin.models.trending_api.TrendingEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface IRemoteRepository {

    fun getTrendingFromRemote(): Flow<Either<List<TrendingEntity>?, Throwable?>>
}

class RemoteRepository @Inject constructor(private val trendingService: TrendingService) :
    IRemoteRepository {

    override fun getTrendingFromRemote(): Flow<Either<List<TrendingEntity>?, Throwable?>> = flow {
        when (val response = trendingService.getRepositoriesAsync()) {
            is ApiResponse.ApiSuccess -> emit(Either.Left(response.data))
            is ApiResponse.ApiFailure -> emit(Either.Right(response.t))
        }
    }.flowOn(Dispatchers.IO)
}