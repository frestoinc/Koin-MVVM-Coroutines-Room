package com.frestoinc.sampleapp_kotlin.api.data.room

import com.frestoinc.sampleapp_kotlin.api.data.model.Repo
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.Repository
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.State


/**
 * Created by frestoinc on 29,February,2020 for SampleApp_Kotlin.
 */
interface RoomRepository : Repository {

    suspend fun getRoomRepo(): State<List<Repo>>

    suspend fun insert(data: List<Repo>): State<Unit>
}

class RoomRepositoryImpl(private val repoDatabase: RepoDatabase) : RoomRepository {

    override suspend fun getRoomRepo(): State<List<Repo>> {
        return request { repoDatabase.repoDao.getAll() }
    }

    override suspend fun insert(data: List<Repo>): State<Unit> {
        return request {
            repoDatabase.repoDao.deleteAll()
            repoDatabase.repoDao.insert(data)
        }
    }
}