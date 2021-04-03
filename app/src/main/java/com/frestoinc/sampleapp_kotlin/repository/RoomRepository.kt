package com.frestoinc.sampleapp_kotlin.repository

import com.frestoinc.sampleapp_kotlin.api.domain.response.State
import com.frestoinc.sampleapp_kotlin.models.trending_api.TrendingEntity
import com.frestoinc.sampleapp_kotlin.repository.trending.TrendingDao
import javax.inject.Inject

interface IRoomRepository {

    suspend fun getTrendingFromLocal(): State<List<TrendingEntity>>

    suspend fun insert(data: List<TrendingEntity>): State<Unit>
}

class RoomRepository @Inject constructor(private val trendingDao: TrendingDao) : IRoomRepository {

    override suspend fun getTrendingFromLocal(): State<List<TrendingEntity>> {
        return try {
            State.Success(trendingDao.getAll())
        } catch (ex: Exception) {
            State.Error(ex)
        }
    }

    override suspend fun insert(data: List<TrendingEntity>): State<Unit> {
        return try {
            State.Success(trendingDao.refreshRepo(data))
        } catch (ex: Exception) {
            State.Error(ex)
        }
    }
}