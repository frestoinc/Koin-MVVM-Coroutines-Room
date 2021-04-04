package com.frestoinc.sampleapp_kotlin.ui.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.frestoinc.sampleapp_kotlin.R
import com.frestoinc.sampleapp_kotlin.di.GlideApp

const val error: String = "Error"

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))

@BindingAdapter("imagePath")
fun getImage(imageView: ImageView, path: String?) {
    GlideApp.with(imageView.context)
        .load(path)
        .centerCrop()
        .placeholder(R.drawable.ic_avatar)
        .error(R.drawable.ic_avatar)
        .fallback(R.drawable.ic_avatar)
        .apply(RequestOptions.circleCropTransform())
        .into(imageView)
}

@BindingAdapter("color")
fun parseColor(textView: AppCompatTextView, stringColor: String?) {
    val color = stringColor ?: "#FFFFFF"
    val colorCoding = Color.parseColor(color)
    textView.compoundDrawablesRelative[0].setTint(colorCoding)
}


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