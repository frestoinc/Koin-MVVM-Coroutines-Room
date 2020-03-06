package com.frestoinc.sampleapp_kotlin.api.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.frestoinc.sampleapp_kotlin.api.view.network.ContentLoadingLayout
import com.frestoinc.sampleapp_kotlin.api.view.network.NetState
import com.frestoinc.sampleapp_kotlin.api.view.network.NetworkState
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : AppCompatActivity(),
    ContentLoadingLayout.OnRequestRetryListener, NetworkState {

    private lateinit var viewDatabinding: T

    private lateinit var viewModel: V

    private lateinit var container: ContentLoadingLayout

    private var networkCallback: ConnectivityManager.NetworkCallback

    private val connectivityManager
        get() = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    init {
        networkCallback = object : ConnectivityManager.NetworkCallback() {
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

    override fun getState(): NetState {
        return loadingContainer.state
    }

    override fun onResume() {
        super.onResume()
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    override fun onPause() {
        super.onPause()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

}