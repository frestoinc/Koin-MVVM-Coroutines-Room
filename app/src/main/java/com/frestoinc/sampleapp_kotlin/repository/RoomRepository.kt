package com.frestoinc.sampleapp_kotlin.repository

import com.frestoinc.sampleapp_kotlin.models.Response
import com.frestoinc.sampleapp_kotlin.models.trending_api.TrendingEntity
import com.frestoinc.sampleapp_kotlin.repository.trending.TrendingDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface IRoomRepository {

    suspend fun getTrendingFromLocal(): Response<List<TrendingEntity>>

    suspend fun insert(data: List<TrendingEntity>)
}

class RoomRepository @Inject constructor(private val trendingDao: TrendingDao) : IRoomRepository {

    override suspend fun getTrendingFromLocal(): Response<List<TrendingEntity>> =
        withContext(Dispatchers.IO) {
            try {
                Response.Success(trendingDao.getAll())
            } catch (ex: Exception) {
                ex.printStackTrace()
                Response.Error(ex)
            }
        }


    override suspend fun insert(data: List<TrendingEntity>) =
        withContext(Dispatchers.IO) {
            try {
                trendingDao.refreshRepo(data)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

}