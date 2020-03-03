package com.frestoinc.sampleapp_kotlin.ui

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.frestoinc.sampleapp_kotlin.api.base.BaseViewHolder
import com.frestoinc.sampleapp_kotlin.api.data.model.Repo
import com.frestoinc.sampleapp_kotlin.databinding.ViewholderDataBinding
import com.frestoinc.sampleapp_kotlin.databinding.ViewholderPlaceholderBinding
import java.util.*

/**
 * Created by frestoinc on 03,March,2020 for SampleApp_Kotlin.
 */
class MainAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Repo>() {

        override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem.id == newItem.id
        }

    }

    private val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            0 -> return LoadingViewHolder(
                ViewholderPlaceholderBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        }
        return MainViewHolder(
            ViewholderDataBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MainViewHolder -> holder.binder.model = differ.currentList[position]
            is LoadingViewHolder -> holder.binder.placeholder = differ.currentList[position]
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (differ.currentList.isEmpty()) {
            return 0
        }
        return 1
    }

    override fun getItemId(position: Int): Long {
        if (differ.currentList.isNotEmpty()) {
            return differ.currentList[position].id!!
        }
        return 0
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Repo>?) {
        differ.submitList(list)
    }

    fun setSortedSource(isName: Boolean) {
        if (differ.currentList.isEmpty()) {
            return
        }
        val list = differ.currentList.toMutableList()
        val comparator: Comparator<Repo> =
            Comparator { o1: Repo, o2: Repo ->
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
        submitList(null)
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

    inner class LoadingViewHolder(val binder: ViewholderPlaceholderBinding) :
        BaseViewHolder(binder.root)

}