package com.frestoinc.sampleapp_kotlin.api.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build


/**
 * Created by frestoinc on 04,March,2020 for SampleApp_Kotlin.
 */
class ConnectionTool(private val receiver: NetWorkReceiverImpl) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent!!.action != null && intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            receiver.onNetworkStateChanged(isNetworkAvailable(context))
        }
    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        val connectivityManager =
            context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return parseConnectivity(
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q, connectivityManager
        )
    }

    private fun parseConnectivity(isQ: Boolean, cm: ConnectivityManager): Boolean {
        return if (isQ) parseFoHigherQ(cm) else parseForLowerQ(cm)
    }

    private fun parseFoHigherQ(connectivityManager: ConnectivityManager): Boolean {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        }
        return false
    }

    private fun parseForLowerQ(connectivityManager: ConnectivityManager): Boolean {
        return try {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            activeNetworkInfo != null && activeNetworkInfo.isConnected
        } catch (e: Exception) {
            false
        }
    }

}