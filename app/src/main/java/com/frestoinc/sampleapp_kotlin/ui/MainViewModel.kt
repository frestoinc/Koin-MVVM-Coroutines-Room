package com.frestoinc.sampleapp_kotlin.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.frestoinc.sampleapp_kotlin.api.data.manager.DataManager
import com.frestoinc.sampleapp_kotlin.api.data.model.Repo
import com.frestoinc.sampleapp_kotlin.api.domain.base.BaseViewModel
import com.frestoinc.sampleapp_kotlin.api.domain.response.State
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Created by frestoinc on 27,February,2020 for SampleApp_Kotlin.
 */
class MainViewModel : BaseViewModel(), KoinComponent {

    private val dataManager: DataManager by inject()

    val liveData: MutableLiveData<State<List<Repo>>> by lazy {
        val data: MutableLiveData<State<List<Repo>>> = MutableLiveData()
        viewModelScope.launch {
            data.postValue(State.Loading())
            data.postValue(dataManager.getRoomRepository())
        }
        return@lazy data
    }

    fun fetchRemoteRepo() {
        viewModelScope.launch {
            liveData.postValue(State.Loading())
            when (val response = dataManager.getRemoteRepository()) {
                is State.Success -> {
                    liveData.postValue(response)
                    if (response.data!!.isEmpty()) {
                        liveData.postValue(State.Error(Exception("List is empty")))
                    } else {
                        refreshRepoList(response.data)
                    }

                }
                is State.Error -> liveData.postValue(response)
            }
        }
    }

    private fun refreshRepoList(list: List<Repo>) {
        viewModelScope.launch {
            dataManager.insert(list)
        }
    }
}