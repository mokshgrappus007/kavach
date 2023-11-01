package com.grappus.kavach.domain.use_case.auth_usecase

import com.grappus.kavach.domain.Response
import com.grappus.kavach.domain.model.request_model.OtpSentRequest
import com.grappus.kavach.domain.repository.AuthRepository

class GetOtp(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(phoneNumber: String): Response<String> {
        val otpRequest = OtpSentRequest(phoneNumber = phoneNumber)
        return authRepository.getOtp(otpRequest = otpRequest)
    }
}