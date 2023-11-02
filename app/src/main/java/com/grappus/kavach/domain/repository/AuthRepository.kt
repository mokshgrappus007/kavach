package com.grappus.kavach.domain.repository

import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.model.request_model.NewUserRequestBody
import com.grappus.kavach.domain.model.request_model.OtpSentRequest
import com.grappus.kavach.domain.model.request_model.OtpVerifyRequest
import com.grappus.kavach.domain.model.response_model.User

interface AuthRepository {
    suspend fun getOtp(otpRequest: OtpSentRequest): ResponseData<Boolean>
    suspend fun verifyOtp(otpVerifyRequest: OtpVerifyRequest): ResponseData<String>
    suspend fun createNewUser(newUserRequestBody: NewUserRequestBody): ResponseData<User>
}