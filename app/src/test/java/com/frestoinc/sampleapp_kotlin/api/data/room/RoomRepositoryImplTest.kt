package com.frestoinc.sampleapp_kotlin.api.data.room

import com.frestoinc.sampleapp_kotlin.api.resourcehandler.State
import com.frestoinc.sampleapp_kotlin.utils.getData
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
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

    @MockK

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
        coEvery { roomRepository.getRoomRepo() } coAnswers { throw result }

        runBlocking {
            assertNotNull(roomRepository.getRoomRepo())
            assert(roomRepository.getRoomRepo() is State.Error)
        }

        coVerify { roomRepository.getRoomRepo() }
    }

    @Test
    fun testGetLocalRepo() {
        coEvery { roomRepository.getRoomRepo() } returns State.success(data)

        runBlocking {
            assert(roomRepository.getRoomRepo() is State.Success)
            when (val source = roomRepository.getRoomRepo()) {
                is State.Success -> {
                    assertNotNull(source)
                    assertEquals(source.data, State.success(data))
                }
            }
        }
    }

    @Test
    fun testInsertRepo() {
        coEvery { roomRepository.insert(data) } returns State.success(Unit)

        runBlocking {
            assert(roomRepository.insert(data) is State.Success)
            when (val source = roomRepository.insert(data)) {
                is State.Success -> {
                    assertNotNull(source)
                }
            }
        }


    }
}