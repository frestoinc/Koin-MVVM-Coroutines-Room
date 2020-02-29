package com.frestoinc.sampleapp_kotlin.di

import androidx.room.Room
import com.frestoinc.sampleapp_kotlin.api.remote.roomDB
import com.frestoinc.sampleapp_kotlin.api.room.RepoDatabase
import org.koin.dsl.module

/**
 * Created by frestoinc on 28,February,2020 for SampleApp_Kotlin.
 */

val roomModule = module {

    single {
        Room.databaseBuilder(get(), RepoDatabase::class.java, roomDB)
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<RepoDatabase>().repoDao
    }

}