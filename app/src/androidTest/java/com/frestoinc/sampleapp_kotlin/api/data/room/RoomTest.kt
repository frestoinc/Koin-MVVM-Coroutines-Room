package com.frestoinc.sampleapp_kotlin.api.data.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.frestoinc.sampleapp_kotlin.api.data.model.Repo
import io.reactivex.internal.util.NotificationLite.getValue
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by frestoinc on 28,February,2020 for SampleApp_Kotlin.
 */
@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var repoDao: RepoDao

    private lateinit var repoDatabase: RepoDatabase

    private lateinit var repo: List<Repo>

    @Before
    fun setUp() {
        val ctx = ApplicationProvider.getApplicationContext<Context>()
        repoDatabase = Room.inMemoryDatabaseBuilder(
            ctx, RepoDatabase::class.java
        ).build()
        repoDao = repoDatabase.repoDao
        repo = arrayListOf(
            Repo(
                0, "author", "name", "avatar",
                "url", "description", "language",
                "languageColor", 1, 2,
                3
            )
        )

    }

    @Test
    fun testInsert() {
        runBlocking {
            repoDao.insert(repo)
            val repoTest = getValue<List<Repo>>(repoDao.getAll())
            assertEquals(repoTest.size, 1)
        }
    }

    @Test
    fun testDelete() {
        runBlocking {
            repoDao.insert(repo)
            repoDao.deleteAll()
            assertEquals(repoDao.getAll().size, 0)
        }
    }

    @After
    fun tearDown() {
        repoDatabase.close()
    }
}