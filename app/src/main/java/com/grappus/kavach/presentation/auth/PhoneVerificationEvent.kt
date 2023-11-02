package com.grappus.kavach.presentation.auth

sealed class PhoneVerificationEvent {
    data class PhoneNumberChanges(val value: String) : PhoneVerificationEvent()
    data class SendOtp(val phoneNumber: String) : PhoneVerificationEvent()
}