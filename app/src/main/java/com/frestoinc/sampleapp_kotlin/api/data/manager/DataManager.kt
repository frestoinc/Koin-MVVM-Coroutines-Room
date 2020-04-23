package com.frestoinc.sampleapp_kotlin.api.data.manager

import com.frestoinc.sampleapp_kotlin.api.data.model.Repo
import com.frestoinc.sampleapp_kotlin.api.data.remote.RemoteRepository
import com.frestoinc.sampleapp_kotlin.api.data.room.RoomRepository
import com.frestoinc.sampleapp_kotlin.api.domain.response.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent

/**
 * Created by frestoinc on 23,April,2020 for SampleApp_Kotlin.
 */

class DataManager(
    private val remoteRepository: RemoteRepository,
    private val roomRepository: RoomRepository
) : KoinComponent {

    suspend fun getRemoteRepository(): State<List<Repo>> =
        withContext(Dispatchers.IO) {
            return@withContext remoteRepository.getRemoteRepository()
        }

    suspend fun getRoomRepository(): State<List<Repo>> =
        withContext(Dispatchers.IO) {
            return@withContext roomRepository.getRoomRepository()
        }

    suspend fun insert(data: List<Repo>): State<Unit> =
        withContext(Dispatchers.IO) {
            return@withContext roomRepository.insert(data)
        }
}