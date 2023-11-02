package com.grappus.kavach.domain.model.response_model

data class OtpVerified(
    val isExistingUser: Boolean,
    val authToken: String,
)
