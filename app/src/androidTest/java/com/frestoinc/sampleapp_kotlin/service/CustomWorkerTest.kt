package com.frestoinc.sampleapp_kotlin.service

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by frestoinc on 19,March,2020 for SampleApp_Kotlin.
 */
@RunWith(AndroidJUnit4::class)
class CustomWorkerTest {

    private var mContext: Context? = null

    @Before
    fun setUp() {
        mContext = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun testWorker() {
        val inputData = Data.Builder()
            .putLong("SLEEP_DURATION", 10000L)
            .build()
        val worker = TestListenableWorkerBuilder.from(
                mContext!!,
                CustomWorker::class.java
            )
            .setInputData(inputData)
            .build()
        Assert.assertThat(
            worker.startWork(),
            Matchers.`is`(ListenableWorker.Result.success())
        )
    }

    @After
    fun tearDown() {
    }
}