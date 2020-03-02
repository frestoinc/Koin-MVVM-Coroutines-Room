package com.frestoinc.sampleapp_kotlin.api.data.remote

import com.frestoinc.sampleapp_kotlin.api.data.model.Repo
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.Resource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.HttpException
import java.io.File

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
        }
    }

    @Test
    fun testValidHandling() {
        val fileContent = getJSON()
        val data: List<Repo> =
            Gson().fromJson(fileContent, object : TypeToken<List<Repo>>() {}.type)
        runBlocking {
            whenever(remoteApi.getRepositoriesAsync()).thenReturn(data.toDeferred())
        }
        remoteRepository = RemoteRepositoryImpl(remoteApi)
        runBlocking {
            assertEquals(remoteRepository.getRemoteRepository(), Resource.success(data))
        }
    }

    private fun <T> T.toDeferred() = CompletableDeferred(this)

    private fun getJSON(): String {
        val file = File(this.javaClass.classLoader?.getResource("mockRepo.json")?.path)
        return String(file.readBytes())
    }
}