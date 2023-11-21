package com.grappus.kavach.domain.repository

import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.model.request_model.OtpSentRequest
import com.grappus.kavach.domain.model.request_model.OtpVerifyRequest
import com.grappus.kavach.domain.model.response_model.OtpVerified

interface AuthRepository {
    suspend fun getOtp(otpRequest: OtpSentRequest): Boolean
    suspend fun verifyOtp(otpVerifyRequest: OtpVerifyRequest): OtpVerified

    suspend fun getTwitchUserName(): String
}