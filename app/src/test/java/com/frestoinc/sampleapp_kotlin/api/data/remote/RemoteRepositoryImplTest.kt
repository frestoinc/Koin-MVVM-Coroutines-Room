package com.frestoinc.sampleapp_kotlin.api.data.remote

import com.frestoinc.sampleapp_kotlin.api.data.model.Repo
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.Resource
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.State
import com.frestoinc.sampleapp_kotlin.utils.getData
import com.frestoinc.sampleapp_kotlin.utils.toDeferred
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.HttpException

/**
 * Created by frestoinc on 28,February,2020 for SampleApp_Kotlin.
 */
@RunWith(JUnit4::class)
class RemoteRepositoryImplTest {

    private lateinit var remoteApi: RemoteApi

    private lateinit var remoteRepository: RemoteRepository

    private lateinit var mockException: HttpException

    @Before
    fun setUp() {
        remoteApi = mock()
        mockException = mock()

    }

    @Test
    fun testErrorHandling() {
        whenever(mockException.code()).thenReturn(404)
        runBlocking {
            whenever(remoteApi.getRepositoriesAsync()).thenThrow(mockException)
        }
        remoteRepository = RemoteRepositoryImpl(remoteApi)
        runBlocking {
            assertEquals(remoteRepository.getRemoteRepository(), Resource.error(mockException))
            assertEquals(
                remoteRepository.getRemoteRepository().toState(),
                State.Error<List<Repo>>(mockException)
            )
        }
    }

    @Test
    fun testValidHandling() {
        val data = getData(this)
        runBlocking {
            whenever(remoteApi.getRepositoriesAsync()).thenReturn(data.toDeferred())
        }
        remoteRepository = RemoteRepositoryImpl(remoteApi)
        runBlocking {
            assertEquals(remoteRepository.getRemoteRepository(), Resource.success(data))
            assertEquals(remoteRepository.getRemoteRepository().toState(), State.success(data))
        }
    }
}