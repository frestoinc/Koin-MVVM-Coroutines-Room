package com.frestoinc.sampleapp_kotlin.ui.trending

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frestoinc.sampleapp_kotlin.api.domain.response.State
import com.frestoinc.sampleapp_kotlin.helpers.DataHelper
import com.frestoinc.sampleapp_kotlin.models.trending_api.TrendingEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(private val dataHelper: DataHelper) : ViewModel() {

    val liveData: MutableLiveData<State<List<TrendingEntity>>> by lazy {
        val data: MutableLiveData<State<List<TrendingEntity>>> = MutableLiveData()
        viewModelScope.launch {
            data.postValue(State.Loading())
            data.postValue(dataHelper.getRoomRepository())
        }
        return@lazy data
    }

    fun fetchRemoteRepo() {
        viewModelScope.launch {
            liveData.postValue(State.Loading())
            when (val response = dataHelper.getRemoteRepository()) {
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

    private fun refreshRepoList(list: List<TrendingEntity>) {
        viewModelScope.launch {
            dataHelper.insert(list)
        }
    }
}