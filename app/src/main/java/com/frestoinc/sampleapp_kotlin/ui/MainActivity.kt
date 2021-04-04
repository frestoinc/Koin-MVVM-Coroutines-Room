package com.frestoinc.sampleapp_kotlin.ui

import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.frestoinc.sampleapp_kotlin.R
import com.frestoinc.sampleapp_kotlin.base.BaseActivity
import com.frestoinc.sampleapp_kotlin.databinding.ActivityMainBinding
import com.frestoinc.sampleapp_kotlin.helpers.NetworkHelper
import com.frestoinc.sampleapp_kotlin.models.Response
import com.frestoinc.sampleapp_kotlin.models.trending_api.TrendingEntity
import com.frestoinc.sampleapp_kotlin.ui.trending.TrendingAdapter
import com.frestoinc.sampleapp_kotlin.ui.trending.TrendingViewModel
import com.frestoinc.sampleapp_kotlin.ui.view.network.ContentLoadingLayout
import com.frestoinc.sampleapp_kotlin.ui.view.observe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    @Inject
    lateinit var trendingAdapter: TrendingAdapter

    private val viewModel: TrendingViewModel by viewModels()

    @Inject
    override lateinit var networkHelper: NetworkHelper

    override fun getLoadingContainer(): ContentLoadingLayout =
        viewDataBinding.loadingContainer.apply {
            setOnRequestRetryListener(this@MainActivity)
        }

    override fun viewHasBeenCreated() {
        initView()
        initObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_name -> {
                trendingAdapter.setSortedSource(true)
                true
            }
            R.id.menu_stars -> {
                trendingAdapter.setSortedSource(false)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initView() {
        initToolbar()
        initRecyclerview()
        initRefreshLayout()
    }

    private fun initToolbar() {
        viewDataBinding.toolbar.customToolbar.also {
            setSupportActionBar(it)
        }
        supportActionBar?.setDisplayShowTitleEnabled(false)
        viewDataBinding.toolbar.toolbarTitle.text = getString(R.string.toolbar_title)
    }

    private fun initRecyclerview() {
        viewDataBinding.content.containerRc.apply {
            setDemoLayoutReference(R.layout.viewholder_placeholder)
            adapter = trendingAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
            setItemViewCacheSize(10)
        }
    }

    private fun initRefreshLayout() {
        viewDataBinding.content.container.apply {
            setProgressViewOffset(true, 100, 250)
            setOnRefreshListener {
                onRequestRetry()
            }
        }
    }

    private fun initObservers() {
        observe(viewModel.data, ::onRetrieveData)
        observe(viewModel.error, ::onFailure)
    }

    private fun onRetrieveData(state: Response<List<TrendingEntity>>?) {
        println("onRetrieveData: $state")
        when (state) {
            is Response.Success -> {
                val data = state.data ?: emptyList()
                println("data: ${state.data}")
                trendingAdapter.submitList(data)
                onLoading(false)
            }
            is Response.Loading -> onLoading(true)
            is Response.Error -> onFailure(state.t.toString())
        }
    }

    private fun onFailure(error: String?) {
        onLoading(false)
        getLoadingContainer().switchError()
        showSnackBar(error ?: "Unknown Exception")
    }

    private fun onLoading(isLoading: Boolean) {
        viewDataBinding.content.containerRc.also {
            when (isLoading) {
                true -> it.showShimmerAdapter()
                else -> it.hideShimmerAdapter()
            }
            removeSwipeRefreshing()
        }
    }

    private fun removeSwipeRefreshing() {
        viewDataBinding.content.container.also {
            if (it.isRefreshing) {
                it.isRefreshing = false
            }
        }
    }

    override fun onRequestRetry() {
        if (isConnected()) {
            viewModel.onRefresh()
        }

    }
}