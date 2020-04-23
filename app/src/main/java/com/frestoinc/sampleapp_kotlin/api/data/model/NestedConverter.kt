package com.frestoinc.sampleapp_kotlin.api.data.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by frestoinc on 03,March,2020 for SampleApp_Kotlin.
 */
class NestedConverter {
    companion object {

        @TypeConverter
        @JvmStatic
        fun stringToList(data: String?): List<NestedRepo> {
            if (data == null) {
                return emptyList()
            }
            val type = object : TypeToken<List<NestedRepo>>() {}.type
            return Gson().fromJson(data, type)
        }

        @TypeConverter
        @JvmStatic
        fun listToString(list: List<NestedRepo>): String {
            return Gson().toJson(list)
        }
    }
}


