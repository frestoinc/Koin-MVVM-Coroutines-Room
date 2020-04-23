package com.frestoinc.sampleapp_kotlin.di

import androidx.room.Room
import com.frestoinc.sampleapp_kotlin.api.data.room.RepoDatabase
import com.frestoinc.sampleapp_kotlin.api.data.room.RoomRepository
import com.frestoinc.sampleapp_kotlin.api.data.room.RoomRepositoryImpl
import com.frestoinc.sampleapp_kotlin.api.domain.extension.roomDB
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

    single<RoomRepository> { RoomRepositoryImpl(get()) }

    single {
        get<RepoDatabase>().repoDao
    }
}