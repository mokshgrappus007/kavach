package com.grappus.kavach.presentation.auth

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grappus.kavach.domain.ErrorType
import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.use_case.auth_usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.metamask.androidsdk.Dapp
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import io.metamask.androidsdk.Ethereum
import io.metamask.androidsdk.EthereumMethod
import io.metamask.androidsdk.EthereumRequest
import io.metamask.androidsdk.EthereumState
import io.metamask.androidsdk.RequestError
import java.util.UUID

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val ethereum: Ethereum
) : ViewModel() {

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


    init {
        val dapp = Dapp("moksh", "moksh.com")
        connect(dapp)
    }

    val ethereumState = MediatorLiveData<EthereumState>().apply {
        addSource(ethereum.ethereumState) { newEthereumState ->
            value = newEthereumState
        }
    }

    fun connect(
        dapp: Dapp,
    ) {
        ethereum.connect(dapp) { result ->
            if (result is RequestError) {
                Log.e("Ethereum connection error ", result.message)
            } else {
                signMessage(message = "welcome to kavach", address = result.toString())
                Log.v("Ethereum sign success ", result.toString())
            }
        }
    }

    fun disconnect() {
        ethereum.disconnect()
    }

    fun clearSession() {
        ethereum.clearSession()
    }

    fun signMessage(
        message: String,
        address: String,
    ) {
        val params: List<String> = listOf(address, message)

        val signRequest = EthereumRequest(
            UUID.randomUUID().toString(),
            EthereumMethod.ETH_SIGN_TYPED_DATA_V4.value,
            params
        )

        ethereum.sendRequest(signRequest) { result ->
            if (result is RequestError) {
                Log.e("Ethereum sign error ", result.message)
                Log.e("Ethereum sign error ", result.code.toString())
            } else {
                Log.v("Ethereum connection success ", result.toString())
            }
        }
    }
}
