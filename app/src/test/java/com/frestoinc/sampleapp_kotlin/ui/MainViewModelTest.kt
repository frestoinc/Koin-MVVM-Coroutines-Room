package com.frestoinc.sampleapp_kotlin.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.frestoinc.sampleapp_kotlin.api.data.manager.DataManager
import com.frestoinc.sampleapp_kotlin.api.data.remote.toState
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.Resource
import com.frestoinc.sampleapp_kotlin.utils.getData
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * Created by frestoinc on 02,March,2020 for SampleApp_Kotlin.
 */
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = MainCoroutineTestRule()

    @Mock
    private lateinit var dataManager: DataManager

    private lateinit var mainViewModel: MainViewModel

    private val data = getData(this)

    private val result = Resource.success(data)


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dataManager = DataManager(mockk(), mockk())
        mainViewModel = MainViewModel(dataManager)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testValidRepository() = coroutineTestRule.testDispatcher.runBlockingTest {
        coEvery { dataManager.getRemoteRepository() } returns result

        mainViewModel.getStateLiveData().observeForever {}
        mainViewModel.getRemoteRepo()

        verify(dataManager).getRemoteRepository()

        assertTrue(mainViewModel.getStateLiveData().hasObservers())
        assertEquals(mainViewModel.getStateLiveData().value, result.toState())
    }
}