package com.frestoinc.sampleapp_kotlin.api.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.frestoinc.sampleapp_kotlin.api.view.network.ContentLoadingLayout
import com.frestoinc.sampleapp_kotlin.api.view.network.NetState
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : AppCompatActivity(),
    ContentLoadingLayout.OnRequestRetryListener {

    private lateinit var viewDatabinding: T

    private lateinit var viewModel: V

    private lateinit var container: ContentLoadingLayout

    private val connectivityManager
        get() = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkRequest = NetworkRequest.Builder().apply {
        addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
    }.build()


    private var networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                println("onAvailable")
                runOnUiThread { loadingContainer.switchEmpty() }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                println("onLost")
                runOnUiThread { loadingContainer.switchError() }
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                super.onLosing(network, maxMsToLive)
                println("onLosing")
            }

            override fun onUnavailable() {
                super.onUnavailable()
                println("onUnavailable")
                runOnUiThread { loadingContainer.switchError() }
            }
        }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun getViewModel(): V

    protected abstract fun getBindingVariable(): Int

    protected abstract fun getLoadingContainer(): ContentLoadingLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDataBinding()
    }

    private fun setDataBinding() {
        viewDatabinding = DataBindingUtil.setContentView(this, getLayoutId())
        viewModel = getViewModel()
        viewDatabinding.setVariable(getBindingVariable(), viewModel)
        viewDatabinding.executePendingBindings()
    }

    fun getViewDataBinding(): T {
        return viewDatabinding
    }

    override fun onResume() {
        super.onResume()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onPause() {
        super.onPause()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    override fun onBackPressed() {
        if (loadingContainer.getState() == NetState.ERROR) {
            loadingContainer.dismiss()
        } else {
            super.onBackPressed()
        }
    }

}