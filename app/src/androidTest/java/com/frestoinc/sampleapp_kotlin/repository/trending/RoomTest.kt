package com.frestoinc.sampleapp_kotlin.repository.trending

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.frestoinc.sampleapp_kotlin.models.trending_api.TrendingEntity
import io.reactivex.internal.util.NotificationLite.getValue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by frestoinc on 28,February,2020 for SampleApp_Kotlin.
 */
@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var trendingDao: TrendingDao

    private lateinit var trendingDatabase: TrendingDatabase

    private lateinit var trendingEntity: List<TrendingEntity>

    @Before
    fun setUp() {
        val ctx = ApplicationProvider.getApplicationContext<Context>()
        trendingDatabase = Room.inMemoryDatabaseBuilder(
            ctx, TrendingDatabase::class.java
        ).build()
        trendingDao = trendingDatabase.trendingDao
        trendingEntity = arrayListOf(
            TrendingEntity(
                "author", "name", "avatar",
                "url", "description", "language",
                "languageColor", 1, 2,
                3
            )
        )
    }

    @Test
    fun testInsert() {
        runBlocking {
            trendingDao.insert(trendingEntity)
            val repoTest = getValue<List<TrendingEntity>>(trendingDao.getAll())
            assertEquals(repoTest.size, 1)
        }
    }

    @Test
    fun testDelete() {
        runBlocking {
            trendingDao.insert(trendingEntity)
            trendingDao.deleteAll()
            assertEquals(trendingDao.getAll().size, 0)
        }
    }

    @After
    fun tearDown() {
        trendingDatabase.close()
    }
}