package com.frestoinc.sampleapp_kotlin.ui.trending

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frestoinc.sampleapp_kotlin.extensions.Response
import com.frestoinc.sampleapp_kotlin.extensions.cancelIfActive
import com.frestoinc.sampleapp_kotlin.models.trending_api.TrendingEntity
import com.frestoinc.sampleapp_kotlin.repository.IRemoteRepository
import com.frestoinc.sampleapp_kotlin.repository.IRoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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

    private val mutableData: MutableLiveData<Response<List<TrendingEntity>>> = MutableLiveData()
    private var job: Job? = null

    val liveData = mutableData

    init {
        loadLocal()
    }

    fun onRefresh() {
        fetchRemoteRepo()
    }

    private fun loadLocal() {
        job.cancelIfActive()
        job = viewModelScope.launch {
            roomRepository.getTrendingFromLocal()
                .onStart { mutableData.value = Response.Loading() }
                .catch { e -> handleError(e) }
                .collect {
                    it.fold(
                        { list ->
                            list?.let {
                                mutableData.value = Response.Success(list)
                            } ?: handleError(Throwable("list returns empty"))
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
        job.cancelIfActive()
        job = viewModelScope.launch {
            remoteRepository.getTrendingFromRemote()
                .onStart { mutableData.value = Response.Loading() }
                .catch { e -> handleError(e) }
                .collect {
                    it.fold(
                        { list ->
                            list?.let {
                                mutableData.value = Response.Success(list)
                                launch {
                                    refreshRepoList(list)
                                }
                            } ?: handleError(Throwable("list returns empty"))
                        }, { error ->
                            error?.let {
                                handleError(error)
                            } ?: handleError(Throwable("Unknown Exception"))

                        })
                }
        }
    }

    private fun handleError(t: Throwable) {
        mutableData.value = Response.Error(t)
    }

    private fun refreshRepoList(list: List<TrendingEntity>) {
        viewModelScope.launch {
            roomRepository.insert(list)
        }
    }
}