package com.frestoinc.sampleapp_kotlin.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.frestoinc.sampleapp_kotlin.api.base.BaseViewModel
import com.frestoinc.sampleapp_kotlin.api.data.manager.DataManager
import com.frestoinc.sampleapp_kotlin.api.data.model.Repo
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.Resource
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
        launch {
            liveData.postValue(State.loading())
            when (val result = dataManager.getRoomRepo()) {
                is Resource.Success -> {
                    if (result.data!!.isEmpty()) {
                        getRemoteRepo()
                    } else {
                        liveData.postValue(State.success(result.data))
                    }
                }
                is Resource.Error -> postError(result.exception)
            }
        }
        return@lazy liveData
    }

    fun getStateLiveData(): LiveData<State<List<Repo>>> = _data

    fun getRemoteRepo() {
        _data.postValue(State.loading())
        launch {
            when (val result = dataManager.getRemoteRepository()) {
                is Resource.Success -> storeRepo(result.data!!)
                is Resource.Error -> postError(result.exception)
            }
        }
    }

    private fun getLocalRepo() {
        _data.postValue(State.loading())
        launch {
            when (val result = dataManager.getRoomRepo()) {
                is Resource.Success -> {
                    if (result.data!!.isEmpty()) {
                        getRemoteRepo()
                    } else {
                        println(result.data)
                        _data.postValue(State.success(result.data))
                    }
                }
                is Resource.Error -> postError(result.exception)
            }
        }
    }

    private fun storeRepo(list: List<Repo>) {
        _data.postValue(State.loading())
        launch {
            when (val result = dataManager.insert(list)) {
                is Resource.Success -> getLocalRepo()
                is Resource.Error -> postError(result.exception)
            }
        }
    }

    fun deleteRepo() {
        _data.postValue(State.loading())
        launch {
            when (val result = dataManager.deleteAll()) {
                is Resource.Success -> _data.postValue(State.success(arrayListOf()))
                is Resource.Error -> postError(result.exception)
            }
        }
    }

    override fun postError(exception: Exception) {
        _data.postValue(State.error(exception))
    }
}