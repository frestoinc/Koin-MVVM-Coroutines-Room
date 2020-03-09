package com.frestoinc.sampleapp_kotlin.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.frestoinc.sampleapp_kotlin.R
import com.frestoinc.sampleapp_kotlin.api.base.BaseActivity
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.State
import com.frestoinc.sampleapp_kotlin.api.view.network.ContentLoadingLayout
import com.frestoinc.sampleapp_kotlin.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_content.*
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private lateinit var mainAdapter: MainAdapter

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModel(): MainViewModel {
        val mainViewModel: MainViewModel by viewModel()
        return mainViewModel
    }

    override fun getBindingVariable(): Int {
        return com.frestoinc.sampleapp_kotlin.BR.mainViewModel
    }

    override fun getLoadingContainer(): ContentLoadingLayout {
        loadingContainer.setOnRequestRetryListener(this)
        return loadingContainer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                mainAdapter.setSortedSource(true)
                true
            }
            R.id.menu_stars -> {
                mainAdapter.setSortedSource(false)
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
        val toolbar = getViewDataBinding().toolbar.customToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        getViewDataBinding().toolbar.toolbarTitle.text = getString(R.string.toolbar_title)
    }

    private fun initRecyclerview() {
        val manager = LinearLayoutManager(this@MainActivity)
        val decoration = DividerItemDecoration(this@MainActivity, manager.orientation)
        getViewDataBinding().content.containerRc.apply {
            setDemoLayoutReference(R.layout.viewholder_placeholder)
            mainAdapter = MainAdapter()
            setHasFixedSize(true)
            layoutManager = manager
            addItemDecoration(decoration)
            setItemViewCacheSize(10)
            adapter = mainAdapter
        }
    }

    private fun initRefreshLayout() {
        getViewDataBinding().content.container.setOnRefreshListener { getViewModel().getRemoteRepo() }
    }

    private fun initObservers() {
        getViewModel().getStateLiveData().observeForever {
            when (it) {
                is State.Loading -> containerRc.showShimmerAdapter()
                is State.Success -> {
                    mainAdapter.submitList(it.data)
                    containerRc.hideShimmerAdapter()
                }
                is State.Error -> {
                    containerRc.hideShimmerAdapter()
                    getLoadingContainer().switchError()
                }
            }
            removeSwipeRefreshing()
        }
    }

    private fun removeSwipeRefreshing() {
        if (getViewDataBinding().content.container.isRefreshing) {
            getViewDataBinding().content.container.isRefreshing = false
        }
    }

    override fun onRequestRetry() {
        getViewModel().getRemoteRepo()
    }
}