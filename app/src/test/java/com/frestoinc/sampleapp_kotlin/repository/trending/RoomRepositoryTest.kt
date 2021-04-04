package com.frestoinc.sampleapp_kotlin.repository.trending

import com.frestoinc.sampleapp_kotlin.api.domain.response.State
import com.frestoinc.sampleapp_kotlin.utils.getData
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by frestoinc on 03,March,2020 for SampleApp_Kotlin.
 */
@RunWith(JUnit4::class)
class RoomRepositoryTest {

    @MockK
    private lateinit var roomDatabase: TrendingDatabase

    @MockK
    private lateinit var IRoomRepository: IRoomRepository

    private var data = getData(this)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        IRoomRepository = RoomRepository(roomDatabase)
        val dao = mockk<TrendingDao>()
        every { roomDatabase.trendingDao } returns dao
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testErrorHandling() {
        val result = Exception("Fail")
        coEvery { IRoomRepository.getTrendingFromLocal() } coAnswers { throw result }

        runBlocking {
            assertNotNull(IRoomRepository.getTrendingFromLocal())
            assert(IRoomRepository.getTrendingFromLocal() is State.Error)
        }

        coVerify { IRoomRepository.getTrendingFromLocal() }
    }

    @Test
    fun testGetLocalRepo() {
        coEvery { IRoomRepository.getTrendingFromLocal() } returns State.Success(data)

        runBlocking {
            assert(IRoomRepository.getTrendingFromLocal() is State.Success)
            when (val source = IRoomRepository.getTrendingFromLocal()) {
                is State.Success -> {
                    assertNotNull(source.data)
                    assertEquals(source.data, data)
                }
            }
        }
    }

    @Test
    fun testInsertRepo() {
        coEvery { IRoomRepository.insert(data) } returns State.Success(Unit)

        runBlocking {
            assert(IRoomRepository.insert(data) is State.Success)
            when (val source = IRoomRepository.insert(data)) {
                is State.Success -> {
                    assertNotNull(source)
                    assertNull(source.ex)
                }
            }
        }


    }
}