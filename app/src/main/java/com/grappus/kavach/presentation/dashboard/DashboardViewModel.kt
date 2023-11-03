package com.grappus.kavach.presentation.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.model.response_model.Content
import com.grappus.kavach.domain.use_case.content_usecase.ContentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val contentUseCase: ContentUseCase
) : ViewModel() {

    var dashboardForYouUiState by mutableStateOf(UiState<Content>())
        private set
    var dashboardReadUiState by mutableStateOf(UiState<Content>())
        private set
    var dashboardWatchUiState by mutableStateOf(UiState<Content>())
        private set
    var dashboardListenUiState by mutableStateOf(UiState<Content>())
        private set

    init {
        viewModelScope.launch {
            val getAllForYourContentDeferred = async { getAllForYouContent(personalized = true) }
            getAllForYourContentDeferred.await()

            val getAllReadContentDeferred = async { getAllReadUiContent(contentType = "TEXT") }
            getAllReadContentDeferred.await()

            val getAllWatchContentDeferred = async { getAllWatchUiContent(contentType = "VIDEO") }
            getAllWatchContentDeferred.await()

            val getAllListenContentDeferred = async { getAllListenUiContent(contentType = "AUDIO") }
            getAllListenContentDeferred.await()
        }
    }

    private fun getAllForYouContent(contentType: String? = null, personalized: Boolean = true) {
        dashboardForYouUiState = dashboardForYouUiState.copy(isLoading = true)
        viewModelScope.launch {
            val response =
                contentUseCase.getAllContent(contentType = contentType, personalized = personalized)
            dashboardForYouUiState = when (response) {
                is ResponseData.Success -> {
                    dashboardForYouUiState.copy(isLoading = false, data = response.data)
                }

                is ResponseData.Error -> {
                    dashboardForYouUiState.copy(isLoading = false, error = response.message)
                }
            }
        }
    }

    private fun getAllReadUiContent(contentType: String? = null, personalized: Boolean = true) {
        dashboardReadUiState = dashboardReadUiState.copy(isLoading = true)
        viewModelScope.launch {
            val response =
                contentUseCase.getAllContent(contentType = contentType, personalized = personalized)
            dashboardReadUiState = when (response) {
                is ResponseData.Success -> {
                    dashboardForYouUiState.copy(isLoading = false, data = response.data)
                }

                is ResponseData.Error -> {
                    dashboardReadUiState.copy(isLoading = false, error = response.message)
                }
            }
        }
    }

    private fun getAllWatchUiContent(contentType: String? = null, personalized: Boolean = true) {
        dashboardWatchUiState = dashboardWatchUiState.copy(isLoading = true)
        viewModelScope.launch {
            val response =
                contentUseCase.getAllContent(contentType = contentType, personalized = personalized)
            dashboardWatchUiState = when (response) {
                is ResponseData.Success -> {
                    dashboardForYouUiState.copy(isLoading = false, data = response.data)
                }

                is ResponseData.Error -> {
                    dashboardWatchUiState.copy(isLoading = false, error = response.message)
                }
            }
        }
    }

    private fun getAllListenUiContent(contentType: String? = null, personalized: Boolean = true) {
        dashboardListenUiState = dashboardListenUiState.copy(isLoading = true)
        viewModelScope.launch {
            val response =
                contentUseCase.getAllContent(contentType = contentType, personalized = personalized)
            dashboardListenUiState = when (response) {
                is ResponseData.Success -> {
                    dashboardForYouUiState.copy(isLoading = false, data = response.data)
                }

                is ResponseData.Error -> {
                    dashboardListenUiState.copy(isLoading = false, error = response.message)
                }
            }
        }
    }

}

data class UiState<T>(
    val data: T? = null, val isLoading: Boolean = true, val error: String? = null
)
