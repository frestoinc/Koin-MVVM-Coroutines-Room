package com.frestoinc.sampleapp_kotlin.base

import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView)