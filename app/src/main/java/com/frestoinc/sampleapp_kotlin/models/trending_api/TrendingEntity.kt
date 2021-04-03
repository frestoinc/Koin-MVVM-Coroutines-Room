package com.frestoinc.sampleapp_kotlin.models.trending_api

import androidx.annotation.Keep
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Keep
@Entity(tableName = "repo", indices = [Index(value = ["author", "url"], unique = true)])
data class TrendingEntity(

    @ColumnInfo(name = "author")
    @SerializedName("author")
    val author: String?,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String?,

    @ColumnInfo(name = "avatar")
    @SerializedName("avatar")
    val avatar: String?,

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "url")
    @SerializedName("url")
    val url: String,

    @ColumnInfo(name = "description")
    @SerializedName("description")
    val description: String?,

    @ColumnInfo(name = "language")
    @SerializedName("language")
    val language: String?,

    @ColumnInfo(name = "languageColor")
    @SerializedName("languageColor")
    val languageColor: String?,

    @ColumnInfo(name = "stars")
    @SerializedName("stars")
    val stars: Int?,

    @ColumnInfo(name = "forks")
    @SerializedName("forks")
    val forks: Int?,

    @ColumnInfo(name = "currentPeriodStars")
    @SerializedName("currentPeriodStars")
    val currentPeriodStars: Int?,

    @ColumnInfo(name = "builtBy")
    @SerializedName("builtBy")
    val builtBy: List<NestedTrendingEntity>? = emptyList()

)

@Keep
data class NestedTrendingEntity(

    @ColumnInfo(name = "bb_avatar")
    @SerializedName("href")
    val bbHref: String,

    @ColumnInfo(name = "bb_avatar")
    @SerializedName("avatar")
    val bbAvatar: String,

    @ColumnInfo(name = "bb_username")
    @SerializedName("username")
    val bbUsername: String,
)