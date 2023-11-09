package com.grappus.kavach.presentation.dashboard

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.grappus.kavach.domain.model.response_model.Content
import com.grappus.kavach.domain.use_case.content_usecase.ContentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val contentUseCase: ContentUseCase
) : ViewModel() {

    private val _forYouContentFlow = MutableStateFlow<PagingData<Content>>(PagingData.empty())
    val forYouContentFlow: StateFlow<PagingData<Content>> = _forYouContentFlow

    private val _readContentFlow = MutableStateFlow<PagingData<Content>>(PagingData.empty())
    val readContentFlow: StateFlow<PagingData<Content>> = _readContentFlow

    private val _watchContentFlow = MutableStateFlow<PagingData<Content>>(PagingData.empty())
    val watchContentFlow: StateFlow<PagingData<Content>> = _watchContentFlow

    private val _listenContentFlow = MutableStateFlow<PagingData<Content>>(PagingData.empty())
    val listenContentFlow: StateFlow<PagingData<Content>> = _listenContentFlow


    init {
        getForYouContent()
        getReadContent()
        getWatchContent()
        getListenContent()
    }

    private fun getForYouContent(
    ) {
        viewModelScope.launch {
            contentUseCase.getAllContent(contentType = null)
                .cachedIn(viewModelScope).collect {
                    _forYouContentFlow.value = it
                }
        }
    }

    private fun getReadContent(
        contentType: String? = "TEXT",
    ) {
        viewModelScope.launch {
            contentUseCase.getAllContent(contentType = contentType)
                .cachedIn(viewModelScope).collect {
                    _readContentFlow.value = it
                }
        }
    }

    private fun getWatchContent(
        contentType: String? = "VIDEO",
    ) {
        viewModelScope.launch {
            contentUseCase.getAllContent(contentType = contentType)
                .cachedIn(viewModelScope).collect {
                    _watchContentFlow.value = it
                }
        }
    }

    private fun getListenContent(
        contentType: String? = "AUDIO",
    ) {
        viewModelScope.launch {
            contentUseCase.getAllContent(contentType = contentType)
                .cachedIn(viewModelScope).collect {
                    _listenContentFlow.value = it
                }
        }
    }
}
