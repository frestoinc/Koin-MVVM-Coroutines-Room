package com.frestoinc.sampleapp_kotlin.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.frestoinc.sampleapp_kotlin.R
import com.frestoinc.sampleapp_kotlin.api.base.BaseActivity
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.State
import com.frestoinc.sampleapp_kotlin.databinding.ActivityMainBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initObservers()
        getViewModel().getLocalRepo()
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
        //setLoadingContainer(getViewDataBinding().loadingContainer)
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
        getViewModel().getStateLiveData().observe(this, Observer { state ->
            when (state) {
                is State.Loading -> mainAdapter.submitList(emptyList())
                is State.Success -> mainAdapter.submitList(state.data)
                is State.Error -> println("Error()")
            }
            removeSwipeRefreshing()
        })
    }

    private fun removeSwipeRefreshing() {
        if (getViewDataBinding().content.container.isRefreshing) {
            getViewDataBinding().content.container.isRefreshing = false
        }
    }
}