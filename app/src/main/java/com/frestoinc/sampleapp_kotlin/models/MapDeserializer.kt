package com.frestoinc.sampleapp_kotlin.models

import androidx.annotation.Keep
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.internal.LinkedTreeMap
import java.lang.reflect.Type
import kotlin.math.ceil

@Keep
class MapDeserializerDoubleAsIntFix : JsonDeserializer<MutableMap<String?, Any?>?> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): MutableMap<String?, Any?>? = read(json) as MutableMap<String?, Any?>?

    private fun read(data: JsonElement): Any? {
        when {
            data.isJsonArray -> {
                val list: MutableList<Any?> = ArrayList()
                val arr = data.asJsonArray
                for (anArr in arr) {
                    list.add(read(anArr))
                }
                return list
            }
            data.isJsonObject -> {
                val map: MutableMap<String, Any?> = LinkedTreeMap()
                val obj = data.asJsonObject
                val entitySet = obj.entrySet()
                for ((key, value) in entitySet) {
                    map[key] = read(value)
                }
                return map
            }
            data.isJsonPrimitive -> {
                val prim = data.asJsonPrimitive
                when {
                    prim.isBoolean -> {
                        return prim.asBoolean
                    }
                    prim.isString -> {
                        return prim.asString
                    }
                    prim.isNumber -> {
                        val num = prim.asNumber
                        return if (ceil(num.toDouble()) == num.toLong()
                                .toDouble()
                        ) num.toLong() else {
                            num.toDouble()
                        }
                    }
                }
            }
        }
        return null
    }
}
