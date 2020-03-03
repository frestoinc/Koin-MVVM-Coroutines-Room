package com.frestoinc.sampleapp_kotlin.api.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : AppCompatActivity() {

    private lateinit var viewDatabinding: T

    private lateinit var viewModel: V

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun getViewModel(): V

    protected abstract fun getBindingVariable(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDataBinding()
    }

    private fun setDataBinding() {
        viewDatabinding = DataBindingUtil.setContentView(this, getLayoutId())
        this.viewModel = getViewModel()
        viewDatabinding.setVariable(getBindingVariable(), viewModel)
        viewDatabinding.executePendingBindings()
    }

    fun getViewDataBinding(): T {
        return viewDatabinding
    }
}