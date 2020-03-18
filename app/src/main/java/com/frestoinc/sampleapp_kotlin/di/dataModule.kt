package com.frestoinc.sampleapp_kotlin.di

import com.frestoinc.sampleapp_kotlin.api.data.manager.DataManager
import org.koin.dsl.module

/**
 * Created by frestoinc on 28,February,2020 for SampleApp_Kotlin.
 */

val dataModule = module {

    single { DataManager() }

}