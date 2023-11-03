package com.grappus.kavach.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grappus.kavach.domain.model.response_model.ContentListData
import com.grappus.kavach.domain.use_case.content_usecase.ContentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val contentUseCase: ContentUseCase) :
    ViewModel() {

    private val _forYouContentMutableState =
        MutableStateFlow<UiState<List<ContentListData>>>(UiState.Loading)

    val forYouContentState: StateFlow<UiState<List<ContentListData>>> = _forYouContentMutableState

    private val _listenContentMutableState =
        MutableStateFlow<UiState<List<ContentListData>>>(UiState.Loading)

    val listenContentState: StateFlow<UiState<List<ContentListData>>> = _listenContentMutableState

    private val _watchContentMutableState =
        MutableStateFlow<UiState<List<ContentListData>>>(UiState.Loading)

    val watchContentState: StateFlow<UiState<List<ContentListData>>> = _watchContentMutableState

    private val _readContentMutableState =
        MutableStateFlow<UiState<List<ContentListData>>>(UiState.Loading)

    val readContentState: StateFlow<UiState<List<ContentListData>>> = _readContentMutableState

    init {
        viewModelScope.launch {
            contentUseCase.getAllContent(personalized = true)
        }
    }

}

sealed class UiState<out T> {
    data class Success<out T>(val data: T) : UiState<T>()
    data class Failed(val message: String) : UiState<Nothing>()
    object Loading : UiState<Nothing>()
}