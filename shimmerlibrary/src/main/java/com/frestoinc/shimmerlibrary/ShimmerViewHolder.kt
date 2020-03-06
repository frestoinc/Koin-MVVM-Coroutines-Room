package com.frestoinc.shimmerlibrary

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.supercharge.shimmerlayout.ShimmerLayout


/**
 * Created by frestoinc on 06,March,2020 for SampleApp_Kotlin.
 */
class ShimmerViewHolder(inflater: LayoutInflater, parent: ViewGroup?, innerViewResId: Int) :

    RecyclerView.ViewHolder(
        inflater.inflate(
            R.layout.viewholder_shimmer,
            parent,
            false
        )
    ) {

    private val mShimmerLayout: ShimmerLayout = itemView as ShimmerLayout

    fun setShimmerAngle(angle: Int) {
        mShimmerLayout.setShimmerAngle(angle)
    }

    fun setShimmerColor(color: Int) {
        mShimmerLayout.setShimmerColor(color)
    }

    fun setShimmerMaskWidth(maskWidth: Float) {
        mShimmerLayout.setMaskWidth(maskWidth)
    }

    fun setShimmerViewHolderBackground(viewHolderBackground: Drawable?) {
        viewHolderBackground?.let { setBackground(it) }
    }

    fun setShimmerAnimationDuration(duration: Int) {
        mShimmerLayout.setShimmerAnimationDuration(duration)
    }

    fun setAnimationReversed(animationReversed: Boolean) {
        mShimmerLayout.setAnimationReversed(animationReversed)
    }

    fun bind() {
        mShimmerLayout.startShimmerAnimation()
    }

    private fun setBackground(background: Drawable) {
        mShimmerLayout.background = background
    }

    init {
        inflater.inflate(innerViewResId, mShimmerLayout, true)
    }
}