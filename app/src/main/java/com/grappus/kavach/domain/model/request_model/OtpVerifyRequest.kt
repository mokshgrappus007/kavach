package com.grappus.kavach.domain.model.request_model

data class OtpVerifyRequest(
    val phoneNumber: String,
    val otp: String,
)