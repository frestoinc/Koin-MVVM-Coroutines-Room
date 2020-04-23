package com.frestoinc.sampleapp_kotlin.api.domain.extension

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * Created by frestoinc on 11,April,2020 for SampleApp_Kotlin.
 */
interface NetworkState {

    fun isNetworkConnected(): Boolean
}

class NetworkHandler(private val context: Context) : NetworkState {

    override fun isNetworkConnected(): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork

        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
