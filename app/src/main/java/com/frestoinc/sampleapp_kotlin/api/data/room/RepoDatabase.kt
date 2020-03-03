package com.frestoinc.sampleapp_kotlin.api.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.frestoinc.sampleapp_kotlin.api.data.model.NestedConverter
import com.frestoinc.sampleapp_kotlin.api.data.model.Repo

/**
 * Created by frestoinc on 28,February,2020 for SampleApp_Kotlin.
 */

@Database(entities = [Repo::class], version = 1, exportSchema = false)
@TypeConverters(NestedConverter::class)
abstract class RepoDatabase : RoomDatabase() {

    abstract val repoDao: RepoDao
}
