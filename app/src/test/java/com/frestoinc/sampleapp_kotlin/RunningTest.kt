package com.frestoinc.sampleapp_kotlin

import com.frestoinc.sampleapp_kotlin.api.data.remote.IRemoteRepositoryTest
import com.frestoinc.sampleapp_kotlin.api.data.remote.RemoteRepositoryTest
import com.frestoinc.sampleapp_kotlin.api.data.remote.TrendingEntityTest
import com.frestoinc.sampleapp_kotlin.di.CheckModule
import com.frestoinc.sampleapp_kotlin.repository.trending.RoomRepositoryTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Created by frestoinc on 04,March,2020 for SampleApp_Kotlin.
 */

@RunWith(Suite::class)
@Suite.SuiteClasses(
    RemoteRepositoryTest::class,
    IRemoteRepositoryTest::class,
    TrendingEntityTest::class,
    RoomRepositoryTest::class,
    CheckModule::class
)
class RunningTest