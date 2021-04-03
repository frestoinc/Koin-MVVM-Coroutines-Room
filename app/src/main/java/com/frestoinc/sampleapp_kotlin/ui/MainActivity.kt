package com.frestoinc.sampleapp_kotlin.ui

import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.frestoinc.sampleapp_kotlin.R
import com.frestoinc.sampleapp_kotlin.api.domain.extension.observe
import com.frestoinc.sampleapp_kotlin.api.domain.response.State
import com.frestoinc.sampleapp_kotlin.base.BaseActivity
import com.frestoinc.sampleapp_kotlin.databinding.ActivityMainBinding
import com.frestoinc.sampleapp_kotlin.helpers.NetworkHelper
import com.frestoinc.sampleapp_kotlin.models.trending_api.TrendingEntity
import com.frestoinc.sampleapp_kotlin.ui.trending.TrendingAdapter
import com.frestoinc.sampleapp_kotlin.ui.trending.TrendingViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    @Inject
    lateinit var trendingAdapter: TrendingAdapter

    private val viewModel: TrendingViewModel by viewModels()

    @Inject
    override lateinit var networkHelper: NetworkHelper

    /* override fun getLoadingContainer(): ContentLoadingLayout {
         loadingContainer.setOnRequestRetryListener(this)
         return loadingContainer
     }*/

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
        val toolbar = viewDataBinding.toolbar.customToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        viewDataBinding.toolbar.toolbarTitle.text = getString(R.string.toolbar_title)
    }

    private fun initRecyclerview() {
        viewDataBinding.content.containerRc.apply {
            //setDemoLayoutReference(R.layout.viewholder_placeholder)
            trendingAdapter = TrendingAdapter()
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
            setItemViewCacheSize(10)
            adapter = trendingAdapter
        }
    }

    private fun initRefreshLayout() {
        viewDataBinding.content.container.apply {
            setProgressViewOffset(true, 100, 250)
            setOnRefreshListener {
                if (isConnected()) {
                    viewModel.fetchRemoteRepo()
                }
            }
        }
    }

    private fun initObservers() {
        observe(viewModel.liveData, ::onRetrieveData)
    }

    private fun onRetrieveData(state: State<List<TrendingEntity>>?) {
        when (state) {
            is State.Success -> {
                val data = state.data ?: emptyList()
                if (data.isEmpty()) {
                    viewModel.fetchRemoteRepo()
                } else {
                    trendingAdapter.submitList(data)
                }
                onLoading(false)
            }
            is State.Loading -> onLoading(true)
            is State.Error -> onFailure()
        }
    }

    private fun onFailure() {
        // getLoadingContainer().switchError()
    }

    private fun onLoading(isLoading: Boolean?) {
        /*when (isLoading) {
            true -> containerRc.showShimmerAdapter()
            false -> containerRc.hideShimmerAdapter()
        }*/
        removeSwipeRefreshing()
    }

    private fun removeSwipeRefreshing() {
        /*if (container.isRefreshing) {
            container.isRefreshing = false
        }*/
    }

    override fun onRequestRetry() {
        if (isConnected()) {
            viewModel.fetchRemoteRepo()
        }

    }
}