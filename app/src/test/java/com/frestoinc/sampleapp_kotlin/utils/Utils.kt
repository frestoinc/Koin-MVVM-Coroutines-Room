package com.frestoinc.sampleapp_kotlin.utils

import com.frestoinc.sampleapp_kotlin.api.data.model.Repo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CompletableDeferred
import java.io.File

/**
 * Created by frestoinc on 02,March,2020 for SampleApp_Kotlin.
 */

fun <T> T.toDeferred() = CompletableDeferred(this)

fun <T : Any> getData(ctx: T): List<Repo> =
    Gson().fromJson(getJSON(ctx), object : TypeToken<List<Repo>>() {}.type)

fun <T : Any> getJSON(ctx: T): String {
    val file = File(ctx.javaClass.classLoader?.getResource("mockRepo.json")?.path)
    return String(file.readBytes())
}