package com.frestoinc.sampleapp_kotlin.api.data.room

import com.frestoinc.sampleapp_kotlin.api.data.model.Repo
import com.frestoinc.sampleapp_kotlin.api.domain.response.State


/**
 * Created by frestoinc on 29,February,2020 for SampleApp_Kotlin.
 */
interface RoomRepository {

    suspend fun getRoomRepository(): State<List<Repo>>

    suspend fun insert(data: List<Repo>): State<Unit>
}

class RoomRepositoryImpl(private val repoDatabase: RepoDatabase) : RoomRepository {

    override suspend fun getRoomRepository(): State<List<Repo>> {
        return try {
            State.Success(repoDatabase.repoDao.getAll())
        } catch (ex: Exception) {
            State.Error(ex)
        }
    }

    override suspend fun insert(data: List<Repo>): State<Unit> {
        return try {
            State.Success(repoDatabase.repoDao.refreshRepo(data))
        } catch (ex: Exception) {
            State.Error(ex)
        }
    }
}