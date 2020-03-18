package com.frestoinc.sampleapp_kotlin.di

import com.frestoinc.sampleapp_kotlin.api.glide.CustomGlideApp
import com.frestoinc.sampleapp_kotlin.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */

val appModule = module {
    viewModel { MainViewModel() }

    single { CustomGlideApp() }
}