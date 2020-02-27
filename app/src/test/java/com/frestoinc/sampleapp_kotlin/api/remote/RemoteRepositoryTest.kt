package com.frestoinc.sampleapp_kotlin.api.remote

import org.junit.Test
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.nio.charset.StandardCharsets

/**
 * Created by frestoinc on 28,February,2020 for SampleApp_Kotlin.
 */
class RemoteRepositoryTest {

    @Test
    @Throws(IOException::class)
    fun testApi() {
        val conn = URL(baseURL + retrofitField).openConnection()
        val inputStream = conn.getInputStream()
        val sb = StringBuilder()
        BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
            .use { reader ->
                var s: String?
                while (reader.readLine().also { s = it } != null) {
                    sb.append(s)
                }
            }
        assert(sb.isNotEmpty())
    }
}