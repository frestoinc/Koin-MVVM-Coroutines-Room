package com.frestoinc.sampleapp_kotlin.helpers

import com.frestoinc.sampleapp_kotlin.models.Response
import com.frestoinc.sampleapp_kotlin.models.trending_api.TrendingEntity
import com.frestoinc.sampleapp_kotlin.repository.IRemoteRepository
import com.frestoinc.sampleapp_kotlin.repository.IRoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface IDataHelper {

    suspend fun getRemoteRepository(): Response<List<TrendingEntity>>

    suspend fun getRoomRepository(): Response<List<TrendingEntity>>

    suspend fun insert(data: List<TrendingEntity>)
}

class DataHelper @Inject constructor(
    private val remoteRepository: IRemoteRepository,
    private val roomRepository: IRoomRepository
) : IDataHelper {

    override suspend fun getRemoteRepository(): Response<List<TrendingEntity>> =
        withContext(Dispatchers.IO) {
            return@withContext remoteRepository.getTrendingFromRemote()
        }

    override suspend fun getRoomRepository(): Response<List<TrendingEntity>> =
        withContext(Dispatchers.IO) {
            return@withContext roomRepository.getTrendingFromLocal()
        }

    override suspend fun insert(data: List<TrendingEntity>) =
        withContext(Dispatchers.IO) {
            return@withContext roomRepository.insert(data)
        }
}