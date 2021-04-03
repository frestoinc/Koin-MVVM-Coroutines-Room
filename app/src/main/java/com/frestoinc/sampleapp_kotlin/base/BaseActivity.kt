package com.frestoinc.sampleapp_kotlin.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.frestoinc.sampleapp_kotlin.R
import com.frestoinc.sampleapp_kotlin.helpers.NetworkHelper
import com.frestoinc.sampleapp_kotlin.ui.view.network.ContentLoadingLayout
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes private val layoutId: Int) :
    AppCompatActivity(),
    ContentLoadingLayout.OnRequestRetryListener {

    lateinit var viewDataBinding: T

    private lateinit var container: ContentLoadingLayout

    protected abstract fun viewHasBeenCreated()

    protected abstract val networkHelper: NetworkHelper

    // protected abstract fun getLoadingContainer(): ContentLoadingLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        viewDataBinding.executePendingBindings()
        viewHasBeenCreated()
    }

    internal fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null

    internal fun notify(@StringRes message: Int) =
        Snackbar.make(findViewById(R.id.content), message, Snackbar.LENGTH_LONG).show()

    internal fun isConnected(): Boolean {
        return networkHelper.isNetworkConnected()
    }

    internal fun notifyWithAction(message: String, actionText: String, action: () -> Any) {
        val snackBar =
            Snackbar.make(findViewById(R.id.content), message, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(actionText) { action.invoke() }
        snackBar.setActionTextColor(getColor(android.R.color.holo_red_dark))
        snackBar.show()
    }

}