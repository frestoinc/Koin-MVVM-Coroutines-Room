package com.frestoinc.sampleapp_kotlin.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.frestoinc.sampleapp_kotlin.api.base.BaseViewModel
import com.frestoinc.sampleapp_kotlin.api.data.manager.DataManager
import com.frestoinc.sampleapp_kotlin.api.data.model.Repo
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.State
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */
class MainViewModel : BaseViewModel(), KoinComponent {

    private val dataManager: DataManager by inject()

    private val _data by lazy {
        val liveData = MutableLiveData<State<List<Repo>>>()
        viewModelScope.launch {
            liveData.postValue(State.loading())
            when (val result = dataManager.getRoomRepo()) {
                is State.Success -> {
                    if (result.data.isEmpty()) {
                        getRemoteRepo()
                    } else {
                        liveData.postValue(State.success(result.data))
                    }
                }
                is State.Error -> postError(result.error)
            }
        }
        return@lazy liveData
    }

    fun getStateLiveData(): LiveData<State<List<Repo>>> = _data

    fun getRemoteRepo() {
        _data.postValue(State.loading())
        viewModelScope.launch {
            when (val result = dataManager.getRemoteRepository()) {
                is State.Success -> storeRepo(result.data)
                is State.Error -> postError(result.error)
            }
        }
    }

    private fun getLocalRepo() {
        _data.postValue(State.loading())
        viewModelScope.launch {
            when (val result = dataManager.getRoomRepo()) {
                is State.Success -> {
                    if (result.data.isEmpty()) {
                        getRemoteRepo()
                    } else {
                        println(result.data)
                        _data.postValue(State.success(result.data))
                    }
                }
                is State.Error -> postError(result.error)
            }
        }
    }

    private fun storeRepo(list: List<Repo>) {
        _data.postValue(State.loading())
        viewModelScope.launch {
            when (val result = dataManager.insert(list)) {
                is State.Success -> getLocalRepo()
                is State.Error -> postError(result.error)
            }
        }
    }

    override fun postError(exception: Exception) {
        _data.postValue(State.error(exception))
    }
}