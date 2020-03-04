package com.frestoinc.sampleapp_kotlin.api.view.network

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.frestoinc.sampleapp_kotlin.R

/**
 * Created by frestoinc on 02,February,2020 for SampleApp.
 */
class ContentLoadingLayout(context: Context?, attrs: AttributeSet?) : RelativeLayout(
    context,
    attrs
), View.OnClickListener, ContentLoader {

    private var errorView: View? = null

    internal var state: NetState = NetState.SUCCESS

    private var listener: OnRequestRetryListener? = null

    private fun switchToErrorView() {
        if (errorView == null) {
            errorView =
                LayoutInflater.from(context).inflate(R.layout.layout_error, this, false)
            addView(errorView)
            findViewById<View>(R.id.error_btn).setOnClickListener(this)
        } else {
            errorView!!.visibility = View.VISIBLE
        }
    }

    private fun switchToEmptyView() {
        if (errorView != null) {
            errorView!!.visibility = View.GONE
        }
    }

    fun setOnRequestRetryListener(listener: OnRequestRetryListener?) {
        this.listener = listener
    }

    override fun onClick(v: View) {
        if (listener != null) {
            listener!!.onRequestRetry()
        }
    }

    override fun switchError() {
        state = NetState.ERROR
        switchToErrorView()
    }

    override fun switchEmpty() {
        state = NetState.SUCCESS
        switchToEmptyView()
    }

    interface OnRequestRetryListener {
        fun onRequestRetry()
    }
}