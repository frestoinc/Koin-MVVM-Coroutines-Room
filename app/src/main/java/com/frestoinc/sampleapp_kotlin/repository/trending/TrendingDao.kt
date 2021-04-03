package com.frestoinc.sampleapp_kotlin.repository.trending

import androidx.room.*
import com.frestoinc.sampleapp_kotlin.models.trending_api.TrendingEntity

@Dao
interface TrendingDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(list: List<TrendingEntity>)

    @Query("DELETE FROM repo")
    suspend fun deleteAll()

    @Query("SELECT * from repo ORDER BY author ASC")
    suspend fun getAll(): List<TrendingEntity>

    @Transaction
    suspend fun refreshRepo(list: List<TrendingEntity>) {
        deleteAll()
        insert(list)
    }

}