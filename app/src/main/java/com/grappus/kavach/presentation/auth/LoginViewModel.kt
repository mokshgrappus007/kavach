package com.grappus.kavach.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grappus.kavach.domain.ErrorType
import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.use_case.auth_usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authUseCase: AuthUseCase) : ViewModel() {

    var phoneTextState by mutableStateOf("")
        private set

    val isLoginInProgress = mutableStateOf(false)

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object Navigate : UiEvent()
    }

    fun onEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.PhoneNumberChanges -> {
                phoneTextState = event.value
            }

            is LoginScreenEvent.SendOtp -> {
                sendOtp(event.phoneNumber)
            }
        }
    }

    private fun sendOtp(phoneNumber: String) {
        isLoginInProgress.value = true
        viewModelScope.launch {
            when (val response = authUseCase.sendOtp(phoneNumber = phoneNumber)) {
                is ResponseData.Success -> {
                    verifyOtp(otp = "9999", phoneNumber = phoneNumber)
                    isLoginInProgress.value = false
                }

                is ResponseData.Error -> {
                    if (response.errorType is ErrorType.Generic) {
                        _eventFlow.emit(UiEvent.ShowSnackbar(message = response.errorType.message))
                        isLoginInProgress.value = false
                    }
                }
            }
        }
    }

    private fun verifyOtp(otp: String, phoneNumber: String) {
        viewModelScope.launch {
            when (val response = authUseCase.verifyOtp(phoneNumber = phoneNumber, otp = otp)) {
                is ResponseData.Success -> {
                    _eventFlow.emit(UiEvent.Navigate)
                }

                is ResponseData.Error -> {
                    if (response.errorType is ErrorType.Generic) {
                        _eventFlow.emit(UiEvent.ShowSnackbar(message = response.errorType.message))
                    }
                }
            }
        }
    }
}