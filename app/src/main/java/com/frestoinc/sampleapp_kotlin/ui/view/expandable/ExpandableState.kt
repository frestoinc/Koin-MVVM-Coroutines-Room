package com.frestoinc.sampleapp_kotlin.ui.view.expandable

import androidx.room.TypeConverter

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */
enum class ExpandableState(private val code: Int) {
    COLLAPSED(0), COLLAPSING(1), EXPANDING(2), EXPANDED(3);

    companion object {

        @JvmStatic
        @TypeConverter
        fun getStatusInt(expandableState: ExpandableState?): Int? {
            return expandableState?.code
        }

        fun valueOf(code: Int): ExpandableState? {
            val vals = values()
            return if (code >= 0 && code < vals.size) vals[code] else null
        }
    }

}