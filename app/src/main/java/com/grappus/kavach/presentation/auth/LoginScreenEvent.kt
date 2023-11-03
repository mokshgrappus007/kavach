package com.grappus.kavach.presentation.auth

sealed class LoginScreenEvent {
    data class PhoneNumberChanges(val value: String) : LoginScreenEvent()
    data class SendOtp(val phoneNumber: String) : LoginScreenEvent()
}