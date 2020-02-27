package com.frestoinc.sampleapp_kotlin.di

import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.KoinTest

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */

class CheckModule : KoinTest {

    @Test
    fun checkModule() {
        startKoin { modules(appModule) }
    }
}