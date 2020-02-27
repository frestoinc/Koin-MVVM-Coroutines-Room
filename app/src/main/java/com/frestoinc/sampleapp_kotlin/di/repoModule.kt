package com.frestoinc.sampleapp_kotlin.di

import com.frestoinc.sampleapp_kotlin.api.remote.Repo
import com.frestoinc.sampleapp_kotlin.api.remote.baseURL
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by frestoinc on 28,February,2020 for SampleApp_Kotlin.
 */
val repoModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(get())
            .build()
            .create(Repo::class.java)
    }

    single {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get())
            .connectTimeout(20L, TimeUnit.SECONDS)
            .writeTimeout(20L, TimeUnit.SECONDS)
            .readTimeout(20L, TimeUnit.SECONDS)
            .build()
    }

    single { GsonBuilder().create() }

}