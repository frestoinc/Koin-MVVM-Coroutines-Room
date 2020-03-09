package com.frestoinc.shimmerlibrary

import android.graphics.drawable.Drawable

import android.view.LayoutInflater

import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView


/**
 * Created by frestoinc on 06,March,2020 for SampleApp_Kotlin.
 */
class ShimmerAdapter : RecyclerView.Adapter<ShimmerViewHolder?>() {

    private var mItemCount = 0
    private var mLayoutReference = 0
    private var mShimmerAngle = 0
    private var mShimmerColor = 0
    private var mShimmerDuration = 0
    private var mShimmerMaskWidth = 0f
    private var isAnimationReversed = false
    private var mShimmerItemBackground: Drawable? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShimmerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val shimmerViewHolder =
            ShimmerViewHolder(inflater, parent, mLayoutReference)
        shimmerViewHolder.setShimmerColor(mShimmerColor)
        shimmerViewHolder.setShimmerAngle(mShimmerAngle)
        shimmerViewHolder.setShimmerMaskWidth(mShimmerMaskWidth)
        shimmerViewHolder.setShimmerViewHolderBackground(mShimmerItemBackground)
        shimmerViewHolder.setShimmerAnimationDuration(mShimmerDuration)
        shimmerViewHolder.setAnimationReversed(isAnimationReversed)
        return shimmerViewHolder
    }

    override fun onBindViewHolder(holder: ShimmerViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return mItemCount
    }

    fun setMinItemCount(itemCount: Int) {
        mItemCount = itemCount
    }

    fun setShimmerAngle(shimmerAngle: Int) {
        mShimmerAngle = shimmerAngle
    }

    fun setShimmerColor(shimmerColor: Int) {
        mShimmerColor = shimmerColor
    }

    fun setShimmerMaskWidth(maskWidth: Float) {
        mShimmerMaskWidth = maskWidth
    }

    fun setShimmerItemBackground(shimmerItemBackground: Drawable?) {
        mShimmerItemBackground = shimmerItemBackground
    }

    fun setShimmerDuration(mShimmerDuration: Int) {
        this.mShimmerDuration = mShimmerDuration
    }

    fun setLayoutReference(layoutReference: Int) {
        mLayoutReference = layoutReference
    }

    fun setAnimationReversed(animationReversed: Boolean) {
        isAnimationReversed = animationReversed
    }
}