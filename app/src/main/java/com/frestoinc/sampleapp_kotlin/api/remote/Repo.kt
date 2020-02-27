package com.frestoinc.sampleapp_kotlin.api.remote

import com.google.gson.annotations.SerializedName

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */

data class Repo(

    @SerializedName("author")
    val author: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("avatar")
    val avatar: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("language")
    val language: String,

    @SerializedName("languageColor")
    val languageColor: String,

    @SerializedName("stars")
    val stars: Int,

    @SerializedName("forks")
    val forks: Int,

    @SerializedName("currentPeriodStars")
    val currentPeriodStars: Int,

    @SerializedName("builtBy")
    val builtBy: List<NestedRepo>
) {

    data class NestedRepo(

        @SerializedName("href")
        var href: String,

        @SerializedName("avatar")
        val avatar: String,

        @SerializedName("username")
        val username: String
    )
}