package com.grappus.kavach.presentation.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grappus.kavach.di.DISCORD_CLIENT_ID
import com.grappus.kavach.di.REDIRECT_URI
import com.grappus.kavach.di.SharedPrefManager
import com.grappus.kavach.domain.ErrorType
import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.use_case.auth_usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ResponseTypeValues
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase, private val sharedPreferences: SharedPreferences
) : ViewModel() {

    var phoneTextState by mutableStateOf("")
        private set

    val isLoginInProgress = mutableStateOf(false)

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
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
                        _eventFlow.emit(UiEvent.ShowSnackBar(message = response.errorType.message))
                        isLoginInProgress.value = false
                    }
                }
            }
        }
    }

    fun saveAccessToken(redirectUri: Uri) {
        viewModelScope.launch {
            var token = ""
            redirectUri.fragment!!.split("&").forEach {
                val fragmentPair = it.split("=")
                if (fragmentPair[0] == "accessToken") token = fragmentPair[1]
            }
            sharedPreferences.edit().putString(SharedPrefManager.AUTH_KEY, token).apply()

            _eventFlow.emit(UiEvent.Navigate)
        }
    }

    fun getIntentForDiscordAuth(context: Context): Intent {
        val serviceConfig = AuthorizationServiceConfiguration(
            Uri.parse("https://discord.com/api/oauth2/authorize"),  // authorization endpoint
            Uri.parse("https://discord.com/api/oauth2/token")  // token endpoint
        )

        val authRequestBuilder = AuthorizationRequest.Builder(
            serviceConfig,  // the authorization service configuration
            DISCORD_CLIENT_ID,  // the client ID, typically pre-registered and static
            ResponseTypeValues.TOKEN,  // the response_type value: we want a code
            Uri.parse(REDIRECT_URI)// the redirect URI to which the auth response is sent
        )

        val discordScopes = listOf("identify", "email")

        val authRequest = authRequestBuilder.setScopes(discordScopes).setCodeVerifier(
            null, null, null
        ).build()

        val authService = AuthorizationService(context)

        return authService.getAuthorizationRequestIntent(authRequest)
    }

    private fun verifyOtp(otp: String, phoneNumber: String) {
        viewModelScope.launch {
            when (val response = authUseCase.verifyOtp(phoneNumber = phoneNumber, otp = otp)) {
                is ResponseData.Success -> {
                    _eventFlow.emit(UiEvent.Navigate)
                }

                is ResponseData.Error -> {
                    if (response.errorType is ErrorType.Generic) {
                        _eventFlow.emit(UiEvent.ShowSnackBar(message = response.errorType.message))
                    }
                }
            }
        }
    }
}