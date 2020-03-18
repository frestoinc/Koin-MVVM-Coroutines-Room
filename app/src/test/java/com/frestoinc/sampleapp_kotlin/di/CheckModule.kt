package com.frestoinc.sampleapp_kotlin.di

import android.app.Application
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.After
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.check.checkModules

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */

class CheckModule : KoinTest {

    @MockK
    private lateinit var context: Application

    @Test
    fun checkModule() {
        context = mockk()
        startKoin {
            androidContext(context)
            modules(
                listOf(
                    appModule,
                    networkModule,
                    roomModule,
                    dataModule
                )
            )
        }.checkModules()
    }

    @After
    fun after() {
        stopKoin()
    }
}