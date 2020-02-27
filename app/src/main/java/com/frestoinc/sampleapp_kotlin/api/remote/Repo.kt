package com.frestoinc.sampleapp_kotlin.api.remote

import com.google.gson.annotations.SerializedName

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */

data class Repo(

    @SerializedName("author") val author: String

)