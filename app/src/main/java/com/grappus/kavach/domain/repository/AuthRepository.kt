package com.grappus.kavach.domain.repository

import com.grappus.kavach.domain.Response
import com.grappus.kavach.domain.model.request_model.NewUserRequestBody
import com.grappus.kavach.domain.model.request_model.OtpSentRequest
import com.grappus.kavach.domain.model.request_model.OtpVerifyRequest

interface AuthRepository {
    suspend fun getOtp(otpRequest: OtpSentRequest): Response<String>
    suspend fun verifyOtp(otpVerifyRequest: OtpVerifyRequest): Response<String>
    suspend fun createNewUser(newUserRequestBody: NewUserRequestBody): Response<String>
}