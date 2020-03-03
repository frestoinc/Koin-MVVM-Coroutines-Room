package com.frestoinc.sampleapp_kotlin.api.data.manager

import com.frestoinc.sampleapp_kotlin.api.data.model.Repo
import com.frestoinc.sampleapp_kotlin.api.data.remote.RemoteRepository
import com.frestoinc.sampleapp_kotlin.api.data.room.RoomRepository
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by frestoinc on 29,February,2020 for SampleApp_Kotlin.
 */

class DataManager(
    private val remoteRepository: RemoteRepository,
    private val roomRepository: RoomRepository
) {

    suspend fun getRemoteRepository(): Resource<List<Repo>> =
        withContext(Dispatchers.Main) {
            return@withContext remoteRepository.getRemoteRepository()
        }

    suspend fun getRoomRepo(): Resource<List<Repo>> =
        withContext(Dispatchers.Main) {
            return@withContext roomRepository.getRoomRepo()
        }

    suspend fun insert(data: List<Repo>): Resource<Unit> =
        withContext(Dispatchers.Main) {
            return@withContext roomRepository.insert(data)
        }

    suspend fun deleteAll(): Resource<Unit> =
        withContext(Dispatchers.Main) {
            return@withContext roomRepository.deleteAll()
        }
}