package com.grappus.kavach.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.use_case.auth_usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authUseCase: AuthUseCase) : ViewModel() {

    var phoneTextState by mutableStateOf("")
        private set

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object Navigate : UiEvent()
    }

    fun onEvent(event: PhoneVerificationEvent) {
        when (event) {
            is PhoneVerificationEvent.PhoneNumberChanges -> {
                phoneTextState = event.value
            }

            is PhoneVerificationEvent.SendOtp -> {
                sendOtp(event.phoneNumber)
            }
        }
    }

    fun sendOtp(phoneNumber: String) {
        viewModelScope.launch {
            val response = authUseCase.sendOtp(phoneNumber = phoneNumber)
            when (response) {
                is ResponseData.Success -> {
                    _eventFlow.emit(UiEvent.Navigate)
                }

                is ResponseData.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnackbar(message = response.message))
                }
            }
        }
    }
}