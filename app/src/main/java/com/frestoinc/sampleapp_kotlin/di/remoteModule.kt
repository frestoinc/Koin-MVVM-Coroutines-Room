package com.frestoinc.sampleapp_kotlin.di

import com.frestoinc.sampleapp_kotlin.api.remote.RemoteRepository
import com.frestoinc.sampleapp_kotlin.api.remote.RemoteRepositoryImpl
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.ResponseHandler
import org.koin.dsl.module

/**
 * Created by frestoinc on 28,February,2020 for SampleApp_Kotlin.
 */

val remoteModule = module {

    factory { ResponseHandler() }

    factory { RemoteRepositoryImpl(get(), get()) as RemoteRepository }

}