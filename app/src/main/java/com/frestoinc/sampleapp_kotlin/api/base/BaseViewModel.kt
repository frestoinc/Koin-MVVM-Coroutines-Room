package com.frestoinc.sampleapp_kotlin.api.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */
abstract class BaseViewModel : ViewModel(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}