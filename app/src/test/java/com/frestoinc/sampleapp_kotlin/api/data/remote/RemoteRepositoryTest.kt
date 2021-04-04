package com.frestoinc.sampleapp_kotlin.api.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.frestoinc.sampleapp_kotlin.api.domain.response.State
import com.frestoinc.sampleapp_kotlin.utils.getData
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by frestoinc on 28,February,2020 for SampleApp_Kotlin.
 */
@RunWith(JUnit4::class)
class RemoteRepositoryTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var remoteApi: RemoteApi

    @MockK
    private lateinit var IRemoteRepository: IRemoteRepository


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        IRemoteRepository = RemoteRepository(remoteApi)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testErrorHandling() {
        val result = Exception("Fail")
        coEvery { IRemoteRepository.getTrendingFromRemote() } coAnswers { throw result }

        runBlocking {
            assertNotNull(IRemoteRepository.getTrendingFromRemote())
            assert(IRemoteRepository.getTrendingFromRemote() is State.Error)
        }

        coVerify { IRemoteRepository.getTrendingFromRemote() }
    }

    @Test
    fun testValidHandling() {
        val data = getData(this)
        val result = State.Success(data)
        coEvery { IRemoteRepository.getTrendingFromRemote() } returns result

        runBlocking {
            assert(IRemoteRepository.getTrendingFromRemote() is State.Success)
            when (val source = IRemoteRepository.getTrendingFromRemote()) {
                is State.Success -> {
                    assertNotNull(source.data)
                    assertEquals(source.data, result)
                }

            }
        }
    }
}