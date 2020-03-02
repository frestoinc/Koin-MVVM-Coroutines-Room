package com.frestoinc.sampleapp_kotlin.ui

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Created by frestoinc on 02,March,2020 for SampleApp_Kotlin.
 */
@ExperimentalCoroutinesApi
class MainCoroutineTestRule(val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()) :
    TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}