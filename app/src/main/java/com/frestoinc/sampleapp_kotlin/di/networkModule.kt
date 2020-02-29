package com.frestoinc.sampleapp_kotlin.di

import com.frestoinc.sampleapp_kotlin.BuildConfig
import com.frestoinc.sampleapp_kotlin.api.remote.RemoteApi
import com.frestoinc.sampleapp_kotlin.api.remote.baseURL
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by frestoinc on 28,February,2020 for SampleApp_Kotlin.
 */

private const val CONNECT_TIMEOUT = 20L

val networkModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(get())
            .build()
    }

    single {
        OkHttpClient.Builder().apply {
            cache(get())
            addInterceptor(HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            })
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        }.build()

    }

    single { GsonBuilder().create() }

    factory { get<Retrofit>().create(RemoteApi::class.java) }

    single { Cache(androidApplication().cacheDir, 10L * 1024 * 1024) }

}