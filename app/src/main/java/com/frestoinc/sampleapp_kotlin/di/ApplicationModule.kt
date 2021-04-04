package com.frestoinc.sampleapp_kotlin.di

import android.content.Context
import androidx.room.Room
import com.frestoinc.sampleapp_kotlin.BuildConfig
import com.frestoinc.sampleapp_kotlin.api.ApiCallAdapterFactory
import com.frestoinc.sampleapp_kotlin.api.trending_api.TrendingService
import com.frestoinc.sampleapp_kotlin.models.MapDeserializerDoubleAsIntFix
import com.frestoinc.sampleapp_kotlin.repository.IRemoteRepository
import com.frestoinc.sampleapp_kotlin.repository.IRoomRepository
import com.frestoinc.sampleapp_kotlin.repository.RemoteRepository
import com.frestoinc.sampleapp_kotlin.repository.RoomRepository
import com.frestoinc.sampleapp_kotlin.repository.trending.TrendingDao
import com.frestoinc.sampleapp_kotlin.repository.trending.TrendingDatabase
import com.frestoinc.sampleapp_kotlin.ui.trending.TrendingAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object ApplicationModule {

    private const val TIMEOUT = 20L
    private const val BASE_URL = "https://gtrend.yapie"

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        readTimeout(TIMEOUT, TimeUnit.SECONDS)
    }.build()

    @Provides
    @Singleton
    fun provideRetrofit(url: String): Retrofit = Retrofit.Builder().apply {
        baseUrl(url)
        addCallAdapterFactory(ApiCallAdapterFactory())
        addConverterFactory(GsonConverterFactory.create(provideGson()))
        addCallAdapterFactory(CoroutineCallAdapterFactory())
        client(provideHttpClient())
    }.build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().apply {
        registerTypeAdapter(
            object : TypeToken<Map<String, Any>>() {}.type,
            MapDeserializerDoubleAsIntFix()
        )
        setLenient()
    }.create()

    @Provides
    @Singleton
    fun provideTrendingService(): TrendingService =
        provideRetrofit(BASE_URL).create(TrendingService::class.java)

    @Provides
    @Singleton
    fun provideTrendingDatabase(@ApplicationContext appContext: Context): TrendingDatabase =
        Room.databaseBuilder(appContext, TrendingDatabase::class.java, "trending.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideRepoDao(trendingDatabase: TrendingDatabase): TrendingDao =
        trendingDatabase.trendingDao

    @Provides
    @Singleton
    fun provideRoomRepository(trendingDao: TrendingDao): IRoomRepository =
        RoomRepository(trendingDao)

    @Provides
    @Singleton
    fun provideRemoteRepository(trendingService: TrendingService): IRemoteRepository =
        RemoteRepository(trendingService)

    @Provides
    @Singleton
    fun provideTrendingAdapter() = TrendingAdapter()
}
