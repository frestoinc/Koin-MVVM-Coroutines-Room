package com.frestoinc.sampleapp_kotlin.api.domain.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.frestoinc.sampleapp_kotlin.R
import com.frestoinc.sampleapp_kotlin.api.domain.extension.NetworkHandler
import com.frestoinc.sampleapp_kotlin.api.view.network.ContentLoadingLayout
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : AppCompatActivity(),
    ContentLoadingLayout.OnRequestRetryListener {

    private lateinit var viewDatabinding: T

    private lateinit var viewModel: V

    private lateinit var container: ContentLoadingLayout

    private val networkHandler: NetworkHandler by inject()

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

    internal fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null

    internal fun notify(@StringRes message: Int) =
        Snackbar.make(findViewById(R.id.content), message, Snackbar.LENGTH_LONG).show()

    internal fun isConnected(): Boolean {
        return networkHandler.isNetworkConnected()
    }

    internal fun notifyWithAction(message: String, actionText: String, action: () -> Any) {
        val snackBar =
            Snackbar.make(findViewById(R.id.content), message, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(actionText) { action.invoke() }
        snackBar.setActionTextColor(getColor(android.R.color.holo_red_dark))
        snackBar.show()
    }

}