package com.frestoinc.sampleapp_kotlin.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.frestoinc.sampleapp_kotlin.api.base.BaseViewModel
import com.frestoinc.sampleapp_kotlin.api.data.manager.DataManager
import com.frestoinc.sampleapp_kotlin.api.data.model.Repo
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.Resource
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.State
import kotlinx.coroutines.launch

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */
class MainViewModel(private val dataManager: DataManager) : BaseViewModel() {

    private val _data: MutableLiveData<State<List<Repo>>> = MutableLiveData()

    fun getStateLiveData(): LiveData<State<List<Repo>>> = _data

    fun getRemoteRepo() {
        _data.postValue(State.loading())
        launch {
            when (val result = dataManager.getRemoteRepository()) {
                is Resource.Success -> {
                    storeRepo(result.data)
                }
                is Resource.Error -> postError(result)
            }
        }
    }

    fun getLocalRepo() {
        _data.postValue(State.loading())
        launch {
            when (val result = dataManager.getRoomRepo()) {
                is Resource.Success -> _data.postValue(State.success(result.data))
                is Resource.Error -> postError(result)

            }
        }
    }

    fun storeRepo(list: List<Repo>) {
        _data.postValue(State.loading())
        launch {
            when (val result = dataManager.insert(list)) {
                is Resource.Success -> getLocalRepo()
                is Resource.Error -> postError(result)
            }
        }
    }

    fun deleteRepo() {
        _data.postValue(State.loading())
        launch {
            when (val result = dataManager.deleteAll()) {
                is Resource.Success -> _data.postValue(State.success(arrayListOf()))
                is Resource.Error -> postError(result)
            }
        }
    }

    private fun postError(result: Resource.Error) {
        _data.postValue(State.error(result.exception))
    }
}