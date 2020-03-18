package com.frestoinc.sampleapp_kotlin.di

import com.frestoinc.sampleapp_kotlin.api.data.manager.DataManager
import com.frestoinc.sampleapp_kotlin.api.data.remote.RemoteRepositoryImpl
import com.frestoinc.sampleapp_kotlin.api.data.room.RoomRepositoryImpl
import org.koin.dsl.module

/**
 * Created by frestoinc on 28,February,2020 for SampleApp_Kotlin.
 */

val dataModule = module {

    single { RemoteRepositoryImpl(get()) }

    single { RoomRepositoryImpl(get()) }

    single { DataManager() }

}