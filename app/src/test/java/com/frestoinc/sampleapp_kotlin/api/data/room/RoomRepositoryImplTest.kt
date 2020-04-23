package com.frestoinc.sampleapp_kotlin.api.data.room

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
class RoomRepositoryImplTest {

    @MockK
    private lateinit var roomDatabase: RepoDatabase

    @MockK
    private lateinit var roomRepository: RoomRepository

    private var data = getData(this)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        roomRepository = RoomRepositoryImpl(roomDatabase)
        val dao = mockk<RepoDao>()
        every { roomDatabase.repoDao } returns dao
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testErrorHandling() {
        val result = Exception("Fail")
        coEvery { roomRepository.getRoomRepository() } coAnswers { throw result }

        runBlocking {
            assertNotNull(roomRepository.getRoomRepository())
            assert(roomRepository.getRoomRepository() is State.Error)
        }

        coVerify { roomRepository.getRoomRepository() }
    }

    @Test
    fun testGetLocalRepo() {
        coEvery { roomRepository.getRoomRepository() } returns State.Success(data)

        runBlocking {
            assert(roomRepository.getRoomRepository() is State.Success)
            when (val source = roomRepository.getRoomRepository()) {
                is State.Success -> {
                    assertNotNull(source.data)
                    assertEquals(source.data, data)
                }
            }
        }
    }

    @Test
    fun testInsertRepo() {
        coEvery { roomRepository.insert(data) } returns State.Success(Unit)

        runBlocking {
            assert(roomRepository.insert(data) is State.Success)
            when (val source = roomRepository.insert(data)) {
                is State.Success -> {
                    assertNotNull(source)
                    assertNull(source.ex)
                }
            }
        }


    }
}