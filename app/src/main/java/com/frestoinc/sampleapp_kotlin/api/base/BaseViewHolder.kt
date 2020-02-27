package com.frestoinc.sampleapp_kotlin.api.base

import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */
abstract class BaseViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView)