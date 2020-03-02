package com.frestoinc.sampleapp_kotlin.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.frestoinc.sampleapp_kotlin.api.data.manager.DataManager
import com.frestoinc.sampleapp_kotlin.api.data.model.Repo
import com.frestoinc.sampleapp_kotlin.api.data.remote.toState
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.Resource
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.State
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.timeout
import com.nhaarman.mockitokotlin2.verify
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

    private val observer: Observer<State<List<Repo>>> = mock()

    private val result = Resource.success(arrayListOf<Repo>())


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainViewModel = MainViewModel(dataManager)

    }

    @Test
    fun testValidRepository() = coroutineTestRule.testDispatcher.runBlockingTest {
        doReturn(result).`when`(dataManager).getRemoteRepository()

        mainViewModel.getStateLiveData().observeForever(observer)
        mainViewModel.getRepo()

        verify(dataManager).getRemoteRepository()
        verify(observer, timeout(50)).onChanged(result.toState())

        assertTrue(mainViewModel.getStateLiveData().hasObservers())
        assertEquals(mainViewModel.getStateLiveData().value, result.toState())
    }

    @After
    fun tearDown() {
    }
}