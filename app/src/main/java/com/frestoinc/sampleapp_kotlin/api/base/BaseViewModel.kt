package com.frestoinc.sampleapp_kotlin.api.base

import androidx.lifecycle.ViewModel

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */
abstract class BaseViewModel : ViewModel() {

    abstract fun postError(exception: Exception)
}