package com.frestoinc.sampleapp_kotlin.ui.trending

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.frestoinc.sampleapp_kotlin.base.BaseViewHolder
import com.frestoinc.sampleapp_kotlin.databinding.ViewholderDataBinding
import com.frestoinc.sampleapp_kotlin.models.trending_api.TrendingEntity
import java.util.*
import javax.inject.Inject

class TrendingAdapter @Inject constructor() :
    RecyclerView.Adapter<TrendingAdapter.MainViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<TrendingEntity>() {

        override fun areItemsTheSame(oldItem: TrendingEntity, newItem: TrendingEntity): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: TrendingEntity, newItem: TrendingEntity): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ViewholderDataBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.binder.model = differ.currentList[position]
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(list: List<TrendingEntity>?) {
        differ.submitList(null)
        differ.submitList(list)
    }

    fun setSortedSource(isName: Boolean) {
        if (differ.currentList.isEmpty()) {
            return
        }
        val list = differ.currentList.toMutableList()
        val comparator: Comparator<TrendingEntity> =
            Comparator { o1: TrendingEntity, o2: TrendingEntity ->
                if (isName) {
                    o1.name!!.compareTo(o2.name!!)
                } else {
                    o2.stars!!.compareTo(o1.stars!!)
                }
            }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            list.sortWith(comparator)
        } else {
            Collections.sort(list, comparator)
        }
        submitList(list)
    }

    inner class MainViewHolder(val binder: ViewholderDataBinding) :
        BaseViewHolder(binder.root), View.OnClickListener {

        init {
            binder.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (binder.vexExpandable.isExpanded) {
                binder.vexExpandable.collapse(true)
            } else {
                binder.vexExpandable.expand(true)
            }
        }
    }
}