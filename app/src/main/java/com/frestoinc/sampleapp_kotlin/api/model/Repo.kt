package com.frestoinc.sampleapp_kotlin.api.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */
@Entity(tableName = "repo", indices = [Index(value = ["author", "id"], unique = true)])
data class Repo(

    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "author")
    @SerializedName("author")
    val author: String,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String,

    @ColumnInfo(name = "avatar")
    @SerializedName("avatar")
    val avatar: String,

    @ColumnInfo(name = "url")
    @SerializedName("url")
    val url: String,

    @ColumnInfo(name = "description")
    @SerializedName("description")
    val description: String,

    @ColumnInfo(name = "language")
    @SerializedName("language")
    val language: String,

    @ColumnInfo(name = "languageColor")
    @SerializedName("languageColor")
    val languageColor: String,

    @ColumnInfo(name = "stars")
    @SerializedName("stars")
    val stars: Int,

    @ColumnInfo(name = "forks")
    @SerializedName("forks")
    val forks: Int,

    @ColumnInfo(name = "currentPeriodStars")
    @SerializedName("currentPeriodStars")
    val currentPeriodStars: Int
)