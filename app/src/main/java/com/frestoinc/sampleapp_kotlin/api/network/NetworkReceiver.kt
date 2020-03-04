package com.frestoinc.sampleapp_kotlin.api.network

/**
 * Created by frestoinc on 04,March,2020 for SampleApp_Kotlin.
 */
interface NetworkReceiver {

    fun onNetworkStateChanged(boolean: Boolean)
}

class NetWorkReceiverImpl : NetworkReceiver {

    override fun onNetworkStateChanged(boolean: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}