package com.frestoinc.sampleapp_kotlin.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.frestoinc.sampleapp_kotlin.R
import com.frestoinc.sampleapp_kotlin.api.data.model.Repo
import com.frestoinc.sampleapp_kotlin.api.domain.base.BaseActivity
import com.frestoinc.sampleapp_kotlin.api.domain.extension.observe
import com.frestoinc.sampleapp_kotlin.api.domain.response.State
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
        getViewDataBinding().content.containerRc.apply {
            setDemoLayoutReference(R.layout.viewholder_placeholder)
            mainAdapter = MainAdapter()
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
            setItemViewCacheSize(10)
            adapter = mainAdapter
        }
    }

    private fun initRefreshLayout() {
        getViewDataBinding().content.container.apply {
            setProgressViewOffset(true, 100, 250)
            setOnRefreshListener {
                if (isConnected()) {
                    getViewModel().fetchRemoteRepo()
                }
            }
        }
    }

    private fun initObservers() {
        observe(getViewModel().liveData, ::onRetrieveData)
    }

    private fun onRetrieveData(state: State<List<Repo>>?) {
        when (state) {
            is State.Success -> {
                val data = state.data ?: emptyList()
                if (data.isEmpty()) {
                    getViewModel().fetchRemoteRepo()
                } else {
                    mainAdapter.submitList(data)
                }
                onLoading(false)
            }
            is State.Loading -> onLoading(true)
            is State.Error -> onFailure()
        }
    }

    private fun onFailure() {
        getLoadingContainer().switchError()
    }

    private fun onLoading(isLoading: Boolean?) {
        when (isLoading) {
            true -> containerRc.showShimmerAdapter()
            false -> containerRc.hideShimmerAdapter()
        }
        removeSwipeRefreshing()
    }

    private fun removeSwipeRefreshing() {
        if (container.isRefreshing) {
            container.isRefreshing = false
        }
    }

    override fun onRequestRetry() {
        if (isConnected()) {
            getViewModel().fetchRemoteRepo()
        }

    }
}