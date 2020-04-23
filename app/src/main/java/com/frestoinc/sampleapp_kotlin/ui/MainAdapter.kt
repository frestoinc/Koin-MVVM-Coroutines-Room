package com.frestoinc.sampleapp_kotlin.ui

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.frestoinc.sampleapp_kotlin.api.data.model.Repo
import com.frestoinc.sampleapp_kotlin.api.domain.base.BaseViewHolder
import com.frestoinc.sampleapp_kotlin.databinding.ViewholderDataBinding
import java.util.*

/**
 * Created by frestoinc on 03,March,2020 for SampleApp_Kotlin.
 */
class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Repo>() {

        override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
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

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Repo>?) {
        differ.submitList(null)
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