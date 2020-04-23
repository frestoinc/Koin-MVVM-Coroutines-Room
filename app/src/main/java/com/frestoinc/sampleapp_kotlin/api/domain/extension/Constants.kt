package com.frestoinc.sampleapp_kotlin.api.domain.extension

import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestOptions
import com.frestoinc.sampleapp_kotlin.R
import com.frestoinc.sampleapp_kotlin.api.glide.GlideApp

/**
 * Created by frestoinc on 28,February,2020 for SampleApp_Kotlin.
 */
const val baseURL: String = "https://github-trending-api.now.sh/"
const val retrofitField: String = "repositories"
const val roomDB: String = "repo"

const val error: String = "Error"

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

fun getNetworkFilter(): IntentFilter? {
    return IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION).apply {
        addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
    }
}