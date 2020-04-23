package com.frestoinc.sampleapp_kotlin.api.domain.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.frestoinc.sampleapp_kotlin.api.domain.exception.Failure

/**
 * Created by frestoinc on 11,April,2020 for SampleApp_Kotlin.
 */

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))

fun <L : LiveData<Failure>> LifecycleOwner.failure(liveData: L, body: (Failure?) -> Unit) =
    liveData.observe(this, Observer(body))