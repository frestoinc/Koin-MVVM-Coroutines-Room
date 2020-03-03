package com.frestoinc.sampleapp_kotlin.api.data.room

import com.frestoinc.sampleapp_kotlin.api.data.model.Repo
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.Repository
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.Resource


/**
 * Created by frestoinc on 29,February,2020 for SampleApp_Kotlin.
 */
interface RoomRepository : Repository {

    suspend fun getRoomRepo(): Resource<List<Repo>>

    suspend fun insert(data: List<Repo>): Resource<Unit>

    suspend fun deleteAll(): Resource<Unit>
}

class RoomRepositoryImpl(private val repoDatabase: RepoDatabase) : RoomRepository {

    override suspend fun getRoomRepo(): Resource<List<Repo>> {
        return request { repoDatabase.repoDao.getAll() }
    }

    override suspend fun insert(data: List<Repo>): Resource<Unit> {
        return request { repoDatabase.repoDao.insert(data) }
    }

    override suspend fun deleteAll(): Resource<Unit> {
        return request { repoDatabase.repoDao.deleteAll() }
    }


}