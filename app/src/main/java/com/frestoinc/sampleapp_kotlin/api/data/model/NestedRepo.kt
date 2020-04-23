package com.frestoinc.sampleapp_kotlin.api.data.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

/**
 * Created by frestoinc on 14,April,2020 for SampleApp_Kotlin.
 */

data class NestedRepo(

    @ColumnInfo(name = "avatar")
    @SerializedName("avatar")
    val avatar: String
)