package com.frestoinc.sampleapp_kotlin.di

import org.junit.After
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */

class CheckModule : KoinTest {

    @Test
    fun checkModule() {
        startKoin {
            modules(
                listOf(
                    appModule,
                    networkModule,
                    roomModule,
                    dataModule
                )
            )
        }
    }

    @After
    fun after() {
        stopKoin()
    }
}