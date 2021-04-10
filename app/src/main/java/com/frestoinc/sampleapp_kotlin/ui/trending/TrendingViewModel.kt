package com.frestoinc.sampleapp_kotlin.ui.trending

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frestoinc.sampleapp_kotlin.domain.Response
import com.frestoinc.sampleapp_kotlin.models.trending_api.TrendingEntity
import com.frestoinc.sampleapp_kotlin.repository.IRemoteRepository
import com.frestoinc.sampleapp_kotlin.repository.IRoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val remoteRepository: IRemoteRepository,
    private val roomRepository: IRoomRepository
) : ViewModel() {

    val data: MutableLiveData<Response<List<TrendingEntity>>> = MutableLiveData()

    init {
        loadLocal()
    }

    fun onRefresh() {
        fetchRemoteRepo()
    }

    private fun loadLocal() {
        viewModelScope.launch {
            roomRepository.getTrendingFromLocal()
                .onStart { data.postValue(Response.Loading()) }
                .catch { e -> handleError(e) }
                .collect {
                    it.fold(
                        { list ->
                            list?.let {
                                data.postValue(Response.Success(list))
                            } ?: data.postValue(Response.Success(emptyList()))
                        }, { error ->
                            error?.let {
                                handleError(error)
                            } ?: handleError(Throwable("Unknown Exception"))

                        }
                    )
                }
        }
    }

    private fun fetchRemoteRepo() {
        data.postValue(Response.Loading())
        viewModelScope.launch {
            remoteRepository.getTrendingFromRemote()
                .onStart { data.postValue(Response.Loading()) }
                .catch { e -> handleError(e) }
                .collect {
                    it.fold(
                        { list ->
                            list?.let {
                                data.postValue(Response.Success(list))
                                launch {
                                    refreshRepoList(list)
                                }
                            } ?: data.postValue(Response.Success(emptyList()))
                        }, { error ->
                            error?.let {
                                handleError(error)
                            } ?: handleError(Throwable("Unknown Exception"))

                        })
                }
        }
    }

    private fun handleError(t: Throwable) {
        data.postValue(Response.Error(t))
    }

    private fun refreshRepoList(list: List<TrendingEntity>) {
        viewModelScope.launch {
            roomRepository.insert(list)
        }
    }
}