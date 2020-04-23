package com.frestoinc.sampleapp_kotlin.api.domain.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.frestoinc.sampleapp_kotlin.api.glide.GlideApp

/**
 * Created by frestoinc on 11,April,2020 for SampleApp_Kotlin.
 */

fun View.cancelTransition() {
    transitionName = null
}

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.GONE
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

fun ImageView.loadFromUrl(url: String) =
    GlideApp.with(this.context.applicationContext)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)