package com.frestoinc.sampleapp_kotlin.ui.trending

import androidx.lifecycle.*
import com.frestoinc.sampleapp_kotlin.models.Response
import com.frestoinc.sampleapp_kotlin.models.trending_api.TrendingEntity
import com.frestoinc.sampleapp_kotlin.repository.IRemoteRepository
import com.frestoinc.sampleapp_kotlin.repository.IRoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val remoteRepository: IRemoteRepository,
    private val roomRepository: IRoomRepository
) : ViewModel() {

    val data: MutableLiveData<Response<List<TrendingEntity>>> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()

    init {
        loadLocal()
    }

    fun onRefresh() {
        fetchRemoteRepo()
    }

    private fun loadLocal() {
        data.postValue(Response.Loading())
        viewModelScope.launch {
            when (val response = roomRepository.getTrendingFromLocal()) {
                is Response.Success -> data.postValue(response)
                is Response.Error -> fetchRemoteRepo()
            }
        }
    }

    private fun fetchRemoteRepo() {
        data.postValue(Response.Loading())
        viewModelScope.launch {
            when (val response = remoteRepository.getTrendingFromRemote()) {
                is Response.Success -> {
                    data.postValue(response)
                    refreshRepoList(response.data ?: return@launch)
                }
                is Response.Error -> handleError(response.t)
            }
        }
    }

    private fun handleError(t: Throwable?) {
        error.postValue(t?.toString() ?: "Unknown Exception")
    }

    private fun refreshRepoList(list: List<TrendingEntity>) {
        viewModelScope.launch {
            roomRepository.insert(list)
        }
    }
}