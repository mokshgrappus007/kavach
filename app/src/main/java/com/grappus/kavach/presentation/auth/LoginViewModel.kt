package com.grappus.kavach.presentation.auth

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grappus.kavach.domain.ErrorType
import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.use_case.auth_usecase.AuthUseCase
import com.grappus.kavach.domain.utils.GenericException
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
    private val ethereum: Ethereum,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    var phoneTextState by mutableStateOf("")
        private set

    val isLoginInProgress = mutableStateOf(false)

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    val ethereumState = MediatorLiveData<EthereumState>().apply {
        addSource(ethereum.ethereumState) { newEthereumState ->
            value = newEthereumState
        }
    }

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

    fun connect(
        dapp: Dapp,
    ) {
        if (ethereumState.value?.selectedAddress?.isNotEmpty() == true) {
            disconnect()
            clearSession()
        }
        ethereum.connect(dapp) { result ->
            if (result is RequestError) {
                Log.e("Ethereum connection error ", result.message)
            } else {
                fun signMessage(): String {
                    return "{\"domain\":{\"chainId\":\"0x1\",\"name\":\"Ether Mail\",\"verifyingContract\":\"0xCcCCccccCCCCcCCCCCCcCcCccCcCCCcCcccccccC\",\"version\":\"1\"},\"message\":{\"contents\":\"Hello, Moksh!\",\"from\":{\"name\":\"Moksh\",\"wallets\":[\"0xCD2a3d9F938E13CD947Ec05AbC7FE734Df8DD826\",\"0xDeaDbeefdEAdbeefdEadbEEFdeadbeEFdEaDbeeF\"]},\"to\":[{\"name\":\"Moksh\",\"wallets\":[\"0xbBbBBBBbbBBBbbbBbbBbbbbBBbBbbbbBbBbbBBbB\",\"0xB0BdaBea57B0BDABeA57b0bdABEA57b0BDabEa57\",\"0xB0B0b0b0b0b0B000000000000000000000000000\"]}]},\"primaryType\":\"Mail\",\"types\":{\"EIP712Domain\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"version\",\"type\":\"string\"},{\"name\":\"chainId\",\"type\":\"uint256\"},{\"name\":\"verifyingContract\",\"type\":\"address\"}],\"Group\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"members\",\"type\":\"Person[]\"}],\"Mail\":[{\"name\":\"from\",\"type\":\"Person\"},{\"name\":\"to\",\"type\":\"Person[]\"},{\"name\":\"contents\",\"type\":\"string\"}],\"Person\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"wallets\",\"type\":\"address[]\"}]}}"
                }
                signMessage(
                    message = signMessage(),
                    address = result.toString()
                )
                Log.v("Ethereum connection success ", result.toString())
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
            method = EthereumMethod.ETH_SIGN_TYPED_DATA_V4.value,
            params = params
        )

        ethereum.sendRequest(signRequest) { result ->
            if (result is RequestError) {
                Log.e("Ethereum sign error ", result.message)
                Log.e("Ethereum sign error ", result.code.toString())
            } else {
                viewModelScope.launch { _eventFlow.emit(UiEvent.ShowSnackbar(message = "MetaMask login successfull public address is: $result")) }
                Log.v("Ethereum sign success ", result.toString())
            }
        }
    }

    fun getTwitchUserName(accessToken: String) {
        viewModelScope.launch {
            isLoginInProgress.value = true
            sharedPreferences.edit().putString("TWITCH_ACCESS", accessToken).apply()

            when (val response = authUseCase.getTwitchUser()) {
                is ResponseData.Success -> {
                    isLoginInProgress.value = false
                    _eventFlow.emit(UiEvent.ShowSnackbar(message = "welcome to kavach: ${response.data} 😁"))
                }

                is ResponseData.Error -> {
                    isLoginInProgress.value = false
                    if (response.errorType is ErrorType.Generic) {
                        _eventFlow.emit(UiEvent.ShowSnackbar(message = response.errorType.message))
                    }
                }
            }
        }
    }
}
