package com.frestoinc.sampleapp_kotlin.models

import androidx.room.TypeConverter
import com.frestoinc.sampleapp_kotlin.models.trending_api.NestedTrendingEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NestedConverter {
    companion object {

        @TypeConverter
        @JvmStatic
        fun stringToList(data: String?): List<NestedTrendingEntity> = data?.let {
            val type = object : TypeToken<List<NestedTrendingEntity>>() {}.type
            Gson().fromJson(data, type)
        } ?: emptyList()

        @TypeConverter
        @JvmStatic
        fun listToString(list: List<NestedTrendingEntity>): String = Gson().toJson(list)
    }
}


