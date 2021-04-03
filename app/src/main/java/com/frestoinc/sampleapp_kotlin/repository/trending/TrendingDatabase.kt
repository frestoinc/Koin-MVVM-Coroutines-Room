package com.frestoinc.sampleapp_kotlin.repository.trending

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.frestoinc.sampleapp_kotlin.models.NestedConverter
import com.frestoinc.sampleapp_kotlin.models.trending_api.TrendingEntity

@Database(entities = [TrendingEntity::class], version = 1, exportSchema = false)
@TypeConverters(NestedConverter::class)
abstract class TrendingDatabase : RoomDatabase() {

    abstract val trendingDao: TrendingDao
}
