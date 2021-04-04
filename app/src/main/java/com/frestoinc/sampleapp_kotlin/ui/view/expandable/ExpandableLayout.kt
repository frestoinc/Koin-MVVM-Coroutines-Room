package com.frestoinc.sampleapp_kotlin.ui.view.expandable

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.view.animation.Interpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.frestoinc.sampleapp_kotlin.R
import com.frestoinc.sampleapp_kotlin.ui.view.expandable.ExpandableState.Companion.getStatusInt
import kotlin.math.roundToInt

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */
class ExpandableLayout @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null
) : FrameLayout(context!!, attrs) {

    var duration = DEFAULT_DURATION
    private var parallax = 0f
    private var expansion = 0f
    private var orientation = 0
    /**
     * Get expansion state.
     *
     * @return one of [ExpandableState]
     */
    var state = 0
        private set
    private var interpolator: Interpolator = FastOutSlowInInterpolator()
    private var animator: ValueAnimator? = null
    private var listener: OnExpansionUpdateListener? = null

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        val height = measuredHeight
        val size = if (orientation == LinearLayout.HORIZONTAL) width else height
        visibility = if (expansion == 0f && size == 0) View.GONE else View.VISIBLE
        val expansionDelta = size - (size * expansion).roundToInt()
        if (parallax > 0) {
            val parallaxDelta = expansionDelta * parallax
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                if (orientation == HORIZONTAL) {
                    var direction = -1
                    if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                        direction = 1
                    }
                    child.translationX = direction * parallaxDelta
                } else {
                    child.translationY = -parallaxDelta
                }
            }
        }
        if (orientation == HORIZONTAL) {
            setMeasuredDimension(width - expansionDelta, height)
        } else {
            setMeasuredDimension(width, height - expansionDelta)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        if (animator != null) {
            animator!!.cancel()
        }
        super.onConfigurationChanged(newConfig)
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val bundle = Bundle()
        expansion = if (isExpanded) 1F else 0F
        bundle.putFloat(KEY_EXPANSION, expansion)
        bundle.putParcelable(KEY_SUPER_STATE, superState)
        return bundle
    }

    override fun onRestoreInstanceState(parcelable: Parcelable) {
        val bundle = parcelable as Bundle
        expansion = bundle.getFloat(KEY_EXPANSION)
        state =
            if (expansion == 1f) getStatusInt(
                ExpandableState.EXPANDED
            )!! else getStatusInt(ExpandableState.COLLAPSED)!!
        val superState =
            bundle.getParcelable<Parcelable>(KEY_SUPER_STATE)
        super.onRestoreInstanceState(superState)
    }

    /**
     * Convenience method - same as calling setExpanded(expanded, true).
     */
    var isExpanded: Boolean
        get() = (state == getStatusInt(ExpandableState.EXPANDING)
                || state == getStatusInt(
            ExpandableState.EXPANDED
        ))
        set(expand) {
            setExpanded(expand, true)
        }

    /**
     * Toggle view if animate.
     *
     * @param animate the animate
     */
    @JvmOverloads
    fun toggle(animate: Boolean = true) {
        if (isExpanded) {
            collapse(animate)
        } else {
            expand(animate)
        }
    }

    @JvmOverloads
    fun expand(animate: Boolean = true) {
        setExpanded(true, animate)
    }

    @JvmOverloads
    fun collapse(animate: Boolean = true) {
        setExpanded(false, animate)
    }

    /**
     * Sets expanded.
     *
     * @param expand  the expand
     * @param animate the animate
     */
    private fun setExpanded(expand: Boolean, animate: Boolean) {
        if (expand == isExpanded) {
            return
        }
        val targetExpansion = if (expand) 1 else 0
        if (animate) {
            animateSize(targetExpansion)
        } else {
            setExpansion(targetExpansion.toFloat())
        }
    }

    fun setInterpolator(interpolator: Interpolator) {
        this.interpolator = interpolator
    }

    fun getExpansion(): Float {
        return expansion
    }

    fun setExpansion(expansion: Float) {
        if (this.expansion == expansion) {
            return
        }
        // Infer state from previous value
        val delta = expansion - this.expansion
        when {
            expansion == 0f ->
                state =
                    getStatusInt(ExpandableState.COLLAPSED)!!

            expansion == 1f ->
                state =
                    getStatusInt(ExpandableState.EXPANDED)!!

            delta < 0 ->
                state =
                    getStatusInt(ExpandableState.COLLAPSING)!!

            delta > 0 ->
                state =
                    getStatusInt(ExpandableState.EXPANDING)!!

        }
        visibility = if (state == getStatusInt(
                ExpandableState.COLLAPSED
            )
        ) View.GONE else View.VISIBLE
        this.expansion = expansion
        requestLayout()
        if (listener != null) {
            listener!!.onExpansionUpdate(expansion, state)
        }
    }

    fun getParallax(): Float {
        return parallax
    }

    private fun setParallax(lax: Float) { // Make sure parallax is between 0 and 1
        val parallax = 1f.coerceAtMost(0f.coerceAtLeast(lax))
        this.parallax = parallax
    }

    fun getOrientation(): Int {
        return orientation
    }

    fun setOrientation(orientation: Int) {
        require(!(orientation < 0 || orientation > 1)) { "Orientation must be either 0 (horizontal) or 1 (vertical)" }
        this.orientation = orientation
    }

    fun setOnExpansionUpdateListener(listener: OnExpansionUpdateListener?) {
        this.listener = listener
    }

    private fun animateSize(targetExpansion: Int) {
        if (animator != null) {
            animator!!.cancel()
            animator = null
        }
        animator = ValueAnimator.ofFloat(expansion, targetExpansion.toFloat())
        animator!!.interpolator = interpolator
        animator!!.duration = duration.toLong()
        animator!!.addUpdateListener { valueAnimator: ValueAnimator -> setExpansion(valueAnimator.animatedValue as Float) }
        animator!!.addListener(ExpansionListener(targetExpansion))
        animator!!.start()
    }

    interface OnExpansionUpdateListener {
        /**
         * Callback for expansion updates
         *
         * @param expansionFraction Value between 0 (collapsed) and 1 (expanded) representing the the expansion progress
         * @param state             One of [ExpandableState] repesenting the current expansion state
         */
        fun onExpansionUpdate(expansionFraction: Float, state: Int)
    }

    private inner class ExpansionListener(private val targetExpansion: Int) :
        Animator.AnimatorListener {
        private var canceled = false
        override fun onAnimationStart(animation: Animator) {
            state =
                if (targetExpansion == 0) getStatusInt(
                    ExpandableState.COLLAPSING
                )!! else getStatusInt(ExpandableState.EXPANDING)!!
        }

        override fun onAnimationEnd(animation: Animator) {
            if (!canceled) {
                state =
                    if (targetExpansion == 0) getStatusInt(
                        ExpandableState.COLLAPSED
                    )!! else getStatusInt(ExpandableState.EXPANDED)!!
                setExpansion(targetExpansion.toFloat())
            }
        }

        override fun onAnimationCancel(animation: Animator) {
            canceled = true
        }

        override fun onAnimationRepeat(animation: Animator) {
            //nth
        }

    }

    companion object {
        const val KEY_SUPER_STATE = "super_state"
        const val KEY_EXPANSION = "expansion"
        const val HORIZONTAL = 0
        const val VERTICAL = 1
        private const val DEFAULT_DURATION = 300
    }

    init {
        if (attrs != null) {
            val a =
                getContext().obtainStyledAttributes(attrs, R.styleable.ExpandableLayout)
            duration = a.getInt(
                R.styleable.ExpandableLayout_duration,
                DEFAULT_DURATION
            )
            expansion =
                if (a.getBoolean(R.styleable.ExpandableLayout_expanded, false)) 1F else 0F
            orientation = a.getInt(
                R.styleable.ExpandableLayout_android_orientation,
                VERTICAL
            )
            parallax = a.getFloat(R.styleable.ExpandableLayout_parallax, 1f)
            a.recycle()
            state =
                if (expansion == 0f) getStatusInt(
                    ExpandableState.COLLAPSED
                )!! else getStatusInt(ExpandableState.EXPANDED)!!
            setParallax(parallax)
        }
    }
}