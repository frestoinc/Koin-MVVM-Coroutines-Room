package com.frestoinc.sampleapp_kotlin.ui

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.frestoinc.sampleapp_kotlin.api.domain.response.State
import com.frestoinc.sampleapp_kotlin.di.appModule
import com.frestoinc.sampleapp_kotlin.di.networkModule
import com.frestoinc.sampleapp_kotlin.di.roomModule
import com.frestoinc.sampleapp_kotlin.helpers.DataHelper
import com.frestoinc.sampleapp_kotlin.ui.trending.TrendingViewModel
import com.frestoinc.sampleapp_kotlin.utils.getData
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mockito.verify

/**
 * Created by frestoinc on 02,March,2020 for SampleApp_Kotlin.
 */
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class TrendingViewModelTest : KoinTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = MainCoroutineTestRule()

    @MockK
    private lateinit var context: Application

    @RelaxedMockK
    private lateinit var dataHelper: DataHelper

    @RelaxedMockK
    private lateinit var trendingViewModel: TrendingViewModel

    private val data = getData(this)

    private val result1 = State.Success(data)

    @Before
    fun setUp() {
        stopKoin()
        context = mockk()
        startKoin {
            androidContext(context)
            modules(
                listOf(
                    appModule,
                    networkModule,
                    roomModule
                )
            )
        }
        dataHelper = mockk()
        trendingViewModel = mockk()
    }

    @After
    fun tearDown() {
        unmockkAll()
        stopKoin()
    }

    @Test
    fun testValidRepository() = coroutineTestRule.testDispatcher.runBlockingTest {
        coEvery { dataHelper.getRemoteRepository() } returns result1

        trendingViewModel.liveData.observeForever {}
        trendingViewModel.fetchRemoteRepo()

        verify(dataHelper).getRemoteRepository()

        assertTrue(trendingViewModel.liveData.hasObservers())
        assertEquals(trendingViewModel.liveData.value, result1)
    }
}