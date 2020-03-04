package com.frestoinc.sampleapp_kotlin

import com.frestoinc.sampleapp_kotlin.api.data.remote.RemoteRepositoryImplTest
import com.frestoinc.sampleapp_kotlin.api.data.remote.RemoteRepositoryTest
import com.frestoinc.sampleapp_kotlin.api.data.remote.RepoTest
import com.frestoinc.sampleapp_kotlin.api.data.room.RoomRepositoryImplTest
import com.frestoinc.sampleapp_kotlin.di.CheckModule
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Created by frestoinc on 04,March,2020 for SampleApp_Kotlin.
 */

@RunWith(Suite::class)
@Suite.SuiteClasses(
    RemoteRepositoryImplTest::class,
    RemoteRepositoryTest::class,
    RepoTest::class,
    RoomRepositoryImplTest::class,
    CheckModule::class
)
class RunningTest