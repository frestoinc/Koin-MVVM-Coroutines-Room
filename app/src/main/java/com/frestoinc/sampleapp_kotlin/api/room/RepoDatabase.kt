package com.frestoinc.sampleapp_kotlin.api.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.frestoinc.sampleapp_kotlin.api.model.Repo

/**
 * Created by frestoinc on 28,February,2020 for SampleApp_Kotlin.
 */

@Database(entities = [Repo::class], version = 1, exportSchema = false)
abstract class RepoDatabase : RoomDatabase() {

    abstract val repoDao: RepoDao
}
