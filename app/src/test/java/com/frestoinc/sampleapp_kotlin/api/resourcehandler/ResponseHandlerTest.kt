package com.frestoinc.sampleapp_kotlin.api.resourcehandler

import com.frestoinc.sampleapp_kotlin.api.remote.Repo
import com.nhaarman.mockitokotlin2.mock
import org.hamcrest.core.StringContains.containsString
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */
@RunWith(JUnit4::class)
class ResponseHandlerTest {

    lateinit var responseHandler: ResponseHandler

    @Before
    fun setUp() {
        responseHandler = ResponseHandler()
    }

    @Test
    fun `test exception code is 404 and return error`() {
        val exception = HttpException(Response.error<Repo>(404, mock()))
        val result = responseHandler.onException<List<Repo>>(exception)
        assertThat(result.message, containsString("404"))
    }

    @Test
    fun `test exception code is 501 and return error`() {
        val exception = HttpException(Response.error<Repo>(501, mock()))
        val result = responseHandler.onException<List<Repo>>(exception)
        assertThat(result.message, containsString("Something went wrong"))
    }

    @Test
    fun `test timeout and return error`() {
        val exception = SocketTimeoutException()
        val result = responseHandler.onException<List<Repo>>(exception)
        assertThat(result.message, containsString("Timeout"))
    }


}