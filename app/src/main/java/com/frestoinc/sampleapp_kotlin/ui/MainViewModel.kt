package com.frestoinc.sampleapp_kotlin.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.frestoinc.sampleapp_kotlin.api.base.BaseViewModel
import com.frestoinc.sampleapp_kotlin.api.data.manager.DataManager
import com.frestoinc.sampleapp_kotlin.api.data.model.Repo
import com.frestoinc.sampleapp_kotlin.api.data.remote.toState
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.Resource
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.State
import kotlinx.coroutines.launch

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */
class MainViewModel(private val dataManager: DataManager) : BaseViewModel() {

    private val _data: MutableLiveData<State<List<Repo>>> = MutableLiveData()

    val data: LiveData<State<List<Repo>>>
        get() = _data

    fun getRepo() = viewModelScope.launch {
        _data.postValue(State.loading())
        _data.postValue(dataManager.getRoomRepo().toState())
    }

    fun deleteRepo() = viewModelScope.launch {
        _data.postValue(State.loading())
        when (val status = dataManager.deleteAll()) {
            is Resource.Success -> _data.postValue(State.success(arrayListOf()))
            is Resource.Error -> _data.postValue(State.error(status.exception))
        }

    }
}