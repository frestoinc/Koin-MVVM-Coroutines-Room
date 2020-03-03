package com.frestoinc.sampleapp_kotlin.api.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.frestoinc.sampleapp_kotlin.api.data.model.Repo
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.Resource
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.State
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
class RemoteRepositoryImplTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var remoteApi: RemoteApi

    @MockK
    private lateinit var remoteRepository: RemoteRepository


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        remoteRepository = RemoteRepositoryImpl(remoteApi)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testErrorHandling() {
        val result = Exception("Fail")
        coEvery { remoteRepository.getRemoteRepository() } coAnswers { throw result }

        runBlocking {
            assertNotNull(remoteRepository.getRemoteRepository())
            assert(remoteRepository.getRemoteRepository() == Resource.error<List<Repo>>(result))
            assert(remoteRepository.getRemoteRepository().toState() is State.Error)
        }

        coVerify { remoteRepository.getRemoteRepository() }
    }

    @Test
    fun testValidHandling() {
        val data = getData(this)
        val result = Resource.success(data)
        coEvery { remoteRepository.getRemoteRepository() } returns result

        runBlocking {
            assert(remoteRepository.getRemoteRepository().toState() is State.Success)
            when (val source = remoteRepository.getRemoteRepository()) {
                is Resource.Success -> {
                    assertNotNull(source)
                    assertEquals(source.data, result)
                }
            }

        }
    }
}