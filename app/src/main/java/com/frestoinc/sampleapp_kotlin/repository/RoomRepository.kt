package com.frestoinc.sampleapp_kotlin.repository

import com.frestoinc.sampleapp_kotlin.extensions.Either
import com.frestoinc.sampleapp_kotlin.models.trending_api.TrendingEntity
import com.frestoinc.sampleapp_kotlin.repository.trending.TrendingDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface IRoomRepository {

    fun getTrendingFromLocal(): Flow<Either<List<TrendingEntity>?, Throwable?>>

    suspend fun insert(data: List<TrendingEntity>): Flow<Either<Any, Throwable?>>
}

class RoomRepository @Inject constructor(private val trendingDao: TrendingDao) : IRoomRepository {

    override fun getTrendingFromLocal(): Flow<Either<List<TrendingEntity>?, Throwable?>> = flow {
        try {
            emit(Either.Left(trendingDao.getAll()))
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Either.Right(ex))
        }
    }.flowOn(Dispatchers.IO)


    override suspend fun insert(data: List<TrendingEntity>): Flow<Either<Any, Throwable?>> = flow {
        try {
            trendingDao.refreshRepo(data)
            emit(Either.Left(true))
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Either.Right(ex))
        }
    }.flowOn(Dispatchers.IO)


}