package com.frestoinc.sampleapp_kotlin.api.data.remote

import android.graphics.Color
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestOptions
import com.frestoinc.sampleapp_kotlin.R
import com.frestoinc.sampleapp_kotlin.api.data.glide.GlideApp
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.Resource
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.State

/**
 * Created by frestoinc on 28,February,2020 for SampleApp_Kotlin.
 */
const val baseURL: String = "https://github-trending-api.now.sh/"
const val retrofitField: String = "repositories"
const val roomDB: String = "repo"

fun <T> Resource<T>.toState(): State<T?> {
    return when (this) {
        is Resource.Success -> State.success(data)
        is Resource.Error -> State.error(exception)
    }
}

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